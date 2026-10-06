package com.kumarkd2x.vibeplay

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

/**
 * VibePlayApp
 *
 * Ye Vibe Play ka Application class hai.
 * App start hote hi ye initialize hoti hai.
 *
 * Yahan hum:
 * - Notification channel banate hain (background playback ke liye)
 * - Coil image loader configure karte hain (thumbnails ke liye)
 * - Global settings set karte hain
 */
class VibePlayApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        // Notification Channel banao (Android 8+ ke liye zaroori)
        createNotificationChannel()

        // Global crash handler (optional - debugging ke liye)
        setupCrashHandler()
    }

    /**
     * Notification Channel banata hai background playback ke liye.
     * Iske bina Android 8+ pe notification nahi dikhegi.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Playback Channel (media controls)
            val playbackChannel = NotificationChannel(
                CHANNEL_PLAYBACK,
                "Playback Controls",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Video playback controls notification"
                setShowBadge(false)
                enableLights(false)
                enableVibration(false)
            }

            // Downloads Channel (agar future me download feature add karein)
            val downloadsChannel = NotificationChannel(
                CHANNEL_DOWNLOADS,
                "Downloads",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Video download progress"
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(playbackChannel)
            manager.createNotificationChannel(downloadsChannel)
        }
    }

    /**
     * Coil Image Loader configure karta hai.
     * Thumbnails fast load honge aur cache ho jayenge.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // 25% RAM thumbnails ke liye
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(200L * 1024 * 1024) // 200 MB disk cache
                    .build()
            }
            .crossfade(true) // Smooth fade-in
            .build()
    }

    /**
     * Crash handler - agar app crash ho toh log kar le.
     */
    private fun setupCrashHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Log crash (future me crashlytics add kar sakte hain)
            android.util.Log.e("VibePlayCrash", "Crash on ${thread.name}", throwable)

            // Default handler ko call karo
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    companion object {
        const val CHANNEL_PLAYBACK = "vibe_play_playback"
        const val CHANNEL_DOWNLOADS = "vibe_play_downloads"

        // Global constants
        const val ACCENT_COLOR = 0xFFE50914.toInt()
        const val DARK_BG = 0xFF0A0A0A.toInt()
    }
}
