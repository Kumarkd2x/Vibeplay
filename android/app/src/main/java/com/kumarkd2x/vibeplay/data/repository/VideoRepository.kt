package com.kumarkd2x.vibeplay.data.repository

import com.kumarkd2x.vibeplay.data.api.PipedApi
import com.kumarkd2x.vibeplay.data.api.PipedInstances
import com.kumarkd2x.vibeplay.data.api.PipedSearchItem
import com.kumarkd2x.vibeplay.data.model.Video
import com.kumarkd2x.vibeplay.data.model.VideoDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * VideoRepository
 *
 * Ye class Piped API se data fetch karti hai.
 * Features:
 * - Multiple Piped instances (failover support)
 * - Auto retry
 * - Timeout handling
 * - Results caching (aage Room DB me)
 */
class VideoRepository {

    // ==================== RETROFIT SETUP ====================
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        )
        .build()

    private var api: PipedApi = createApi(PipedInstances.getCurrent())

    private fun createApi(baseUrl: String): PipedApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PipedApi::class.java)
    }

    // ==================== FAILOVER HELPER ====================
    /**
     * Agar ek instance fail ho, toh doosre pe try karo.
     */
    private suspend fun <T> withFailover(
        block: suspend (PipedApi) -> T
    ): Result<T> = withContext(Dispatchers.IO) {
        var lastException: Exception? = null

        repeat(PipedInstances.instances.size) {
            try {
                val result = block(api)
                return@withContext Result.success(result)
            } catch (e: Exception) {
                lastException = e
                // Next instance pe switch karo
                api = createApi(PipedInstances.switchToNext())
            }
        }

        Result.failure(lastException ?: Exception("All Piped instances failed"))
    }

    // ==================== PUBLIC METHODS ====================

    /**
     * Trending videos fetch karo
     */
    suspend fun getTrending(region: String = "IN"): Result<List<Video>> {
        return withFailover { api ->
            val items = api.getTrending(region)
            items // Already List<Video> hai
        }
    }

    /**
     * YouTube pe search karo
     */
    suspend fun search(
        query: String,
        filter: String = "videos"
    ): Result<List<Video>> {
        return withFailover { api ->
            val response = api.search(query, filter)
            response.items.map { it.toVideo() }
        }
    }

    /**
     * Video ki details fetch karo (streams, subtitles, related)
     */
    suspend fun getVideoDetails(videoId: String): Result<VideoDetails> {
        return withFailover { api ->
            api.getVideoDetails(videoId)
        }
    }

    /**
     * Suggestions fetch karo (search autocomplete ke liye)
     */
    suspend fun getSuggestions(query: String): Result<List<String>> {
        return withFailover { api ->
            api.getSuggestions(query)
        }
    }

    // ==================== CONVERTERS ====================

    /**
     * Piped API ka search item ko Video model me convert karta hai
     */
    private fun PipedSearchItem.toVideo(): Video {
        // URL se video ID nikalo
        // "https://www.youtube.com/watch?v=xyz" → "xyz"
        val videoId = url.substringAfter("v=").substringBefore("&").ifEmpty {
            url.substringAfterLast("/")
        }

        return Video(
            id = videoId,
            title = title,
            thumbnailUrl = thumbnail,
            duration = duration,
            views = views,
            uploadedDate = uploadedDate,
            channelName = uploaderName,
            channelAvatarUrl = uploaderAvatar,
            channelId = uploaderUrl.substringAfterLast("/"),
            uploaderVerified = uploaderVerified
        )
    }
}

// ==================== EXTENSION HELPERS ====================

/** YouTube URL se video ID nikalta hai */
fun extractYouTubeId(url: String): String? {
    return when {
        url.contains("youtube.com/watch?v=") ->
            url.substringAfter("v=").substringBefore("&")
        url.contains("youtu.be/") ->
            url.substringAfter("youtu.be/").substringBefore("?").substringBefore("&")
        url.contains("youtube.com/shorts/") ->
            url.substringAfter("shorts/").substringBefore("?").substringBefore("&")
        url.contains("youtube.com/embed/") ->
            url.substringAfter("embed/").substringBefore("?").substringBefore("&")
        url.length == 11 -> url  // Direct video ID
        else -> null
    }
}
