package com.kumarkd2x.vibeplay.ui.screens

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.util.Rational
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPictureAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.kumarkd2x.vibeplay.ui.theme.VibeRed
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(videoUri: Uri, videoTitle: String = "Video", onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val activity = context as? Activity
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = true
        }
    }
    var isPlaying by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }
    var isLocked by remember { mutableStateOf(false) }
    var currentPos by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var playbackSpeed by remember { mutableFloatStateOf(1f) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) { isPlaying = playing }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            currentPos = exoPlayer.currentPosition
            duration = exoPlayer.duration.coerceAtLeast(0L)
            delay(500)
        }
    }

    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying && !isLocked) {
            delay(3000)
            showControls = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(isLocked) {
                if (!isLocked) {
                    detectTapGestures(
                        onTap = { showControls = !showControls },
                        onDoubleTap = { offset ->
                            val w = size.width
                            if (offset.x < w / 3) {
                                exoPlayer.seekTo((exoPlayer.currentPosition - 10000).coerceAtLeast(0))
                            } else if (offset.x > w * 2 / 3) {
                                exoPlayer.seekTo((exoPlayer.currentPosition + 10000).coerceAtMost(duration))
                            } else {
                                if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                            }
                        }
                    )
                }
            }
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f)
                .align(Alignment.Center)
        )

        AnimatedVisibility(
            visible = showControls && !isLocked,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xCC000000))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }
                Text(
                    videoTitle,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        AnimatedVisibility(
            visible = showControls && !isLocked,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { exoPlayer.seekTo((exoPlayer.currentPosition - 10000).coerceAtLeast(0)) },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.FastRewind, "Rewind", tint = Color.White, modifier = Modifier.size(40.dp))
                }
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xCC000000))
                        .clickable {
                            if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(44.dp)
                    )
                }
                IconButton(
                    onClick = { exoPlayer.seekTo((exoPlayer.currentPosition + 10000).coerceAtMost(duration)) },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.FastForward, "Forward", tint = Color.White, modifier = Modifier.size(40.dp))
                }
            }
        }

        AnimatedVisibility(
            visible = showControls && !isLocked,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xCC000000))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(formatTime(currentPos), color = Color.White, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = if (duration > 0) currentPos.toFloat() / duration else 0f,
                        onValueChange = { v -> exoPlayer.seekTo((v * duration).toLong()) },
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = VibeRed,
                            activeTrackColor = VibeRed,
                            inactiveTrackColor = Color(0x4DFFFFFF)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(formatTime(duration), color = Color.White, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { isLocked = true }) {
                        Icon(Icons.Default.Lock, "Lock", tint = Color.White)
                    }
                    IconButton(onClick = {
                        playbackSpeed = when (playbackSpeed) {
                            0.5f -> 0.75f
                            0.75f -> 1f
                            1f -> 1.25f
                            1.25f -> 1.5f
                            1.5f -> 2f
                            else -> 0.5f
                        }
                        exoPlayer.setPlaybackSpeed(playbackSpeed)
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Speed,
                                "Speed",
                                tint = if (playbackSpeed != 1f) VibeRed else Color.White
                            )
                            Text(
                                "${playbackSpeed}x",
                                color = if (playbackSpeed != 1f) VibeRed else Color.White,
                                fontSize = 9.sp
                            )
                        }
                    }
                    IconButton(onClick = {
                        activity?.requestedOrientation =
                            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }) {
                        Icon(Icons.Default.ScreenRotation, "Rotate", tint = Color.White)
                    }
                    IconButton(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val params = PictureInPictureParams.Builder()
                                .setAspectRatio(Rational(16, 9))
                                .build()
                            activity?.enterPictureInPictureMode(params)
                        }
                    }) {
                        Icon(Icons.Default.PictureInPictureAlt, "PiP", tint = Color.White)
                    }
                    IconButton(onClick = {
                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }) {
                        Icon(Icons.Default.Fullscreen, "Fullscreen", tint = Color.White)
                    }
                }
            }
        }

        if (isLocked) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(24.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0x80000000))
                    .clickable { isLocked = false },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.LockOpen, "Unlock", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    if (millis <= 0) return "00:00"
    val s = millis / 1000
    val h = s / 3600
    val m = (s % 3600) / 60
    val sec = s % 60
    return if (h > 0) String.format("%d:%02d:%02d", h, m, sec)
    else String.format("%02d:%02d", m, sec)
}
