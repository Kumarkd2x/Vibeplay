package com.kumarkd2x.vibeplay.data.api

import com.kumarkd2x.vibeplay.data.model.VideoDetails
import com.kumarkd2x.vibeplay.data.model.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * PipedApi
 *
 * Piped ek open-source YouTube API hai jo:
 * - Ads nahi deta
 * - YouTube ka direct data deta hai (video, audio, subtitles)
 * - Multiple instances hain (agar ek down ho toh dusra)
 *
 * Documentation: https://docs.piped.video/
 *
 * NOTE: Ye Retrofit interface hai. Actual calls File #28 (Repository) me honge.
 */

// ==================== PIPED API INSTANCES ====================
// Multiple instances - agar ek fail ho toh doosra try karein
object PipedInstances {
    val instances = listOf(
        "https://pipedapi.kavin.rocks/",
        "https://pipedapi.adminforge.de/",
        "https://api.piped.yt/",
        "https://pipedapi.reallyaweso.me/"
    )

    var currentIndex = 0

    fun getCurrent(): String = instances[currentIndex]

    fun switchToNext(): String {
        currentIndex = (currentIndex + 1) % instances.size
        return instances[currentIndex]
    }
}

// ==================== PIPED API INTERFACE ====================
interface PipedApi {

    /**
     * Trending videos fetch karta hai
     * @param region - Country code (IN, US, etc.)
     */
    @GET("trending")
    suspend fun getTrending(
        @Query("region") region: String = "IN"
    ): List<Video>

    /**
     * Video search karta hai
     * @param query - Search query
     * @param filter - all, videos, channels, etc.
     */
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("filter") filter: String = "videos"
    ): PipedSearchResponse

    /**
     * Video ka detail fetch karta hai (streams, subtitles, etc.)
     * @param videoId - YouTube video ID
     */
    @GET("streams/{videoId}")
    suspend fun getVideoDetails(
        @Path("videoId") videoId: String
    ): VideoDetails

    /**
     * Channel ke videos fetch karta hai
     */
    @GET("channel/{channelId}")
    suspend fun getChannel(
        @Path("channelId") channelId: String
    ): PipedChannelResponse

    /**
     * Suggestions / autocomplete
     */
    @GET("suggestions")
    suspend fun getSuggestions(
        @Query("query") query: String
    ): List<String>
}

// ==================== SEARCH RESPONSE WRAPPER ====================
data class PipedSearchResponse(
    val items: List<PipedSearchItem> = emptyList(),
    val nextpage: String? = null,
    val suggestion: String? = null,
    val corrected: Boolean = false
)

data class PipedSearchItem(
    val url: String = "",
    val type: String = "",         // "stream", "channel", "playlist"
    val title: String = "",
    val thumbnail: String = "",
    val uploaderName: String = "",
    val uploaderUrl: String = "",
    val uploaderAvatar: String = "",
    val uploaderVerified: Boolean = false,
    val duration: Int = 0,
    val views: Long = 0,
    val uploadedDate: String = "",
    val uploaded: Long = 0,
    val shortDescription: String? = null
)

// ==================== CHANNEL RESPONSE ====================
data class PipedChannelResponse(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String = "",
    val description: String = "",
    val subscriberCount: Long = 0,
    val verified: Boolean = false,
    val relatedStreams: List<PipedSearchItem> = emptyList()
)
