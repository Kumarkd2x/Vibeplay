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
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPictureAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun PlayerScreen(
    videoUri: Uri,
    videoTitle: String = "Video",
    onBack: () -> Unit = {}
) {
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
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var playbackSpeed by remember { mutableFloatStateOf(1f) }
    var brightness by remember { mutableFloatStateOf(0.5f) }
    var volume by remember { mutableFloatStateOf(0.5f) }
    var showBrightnessIndicator by remember { mutableStateOf(false) }
    var showVolumeIndicator by remember { mutableStateOf(false) }
    var seekPreviewTime by remember { mutableLongStateOf(0L) }
    var showSeekIndicator by remember { mutableStateOf(false) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            currentPosition = exoPlayer.currentPosition
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
                            val screenWidth = size.width
                            if (offset.x < screenWidth / 3) {
                                exoPlayer.seekTo((exoPlayer.currentPosition - 10000).coerceAtLeast(0))
                                seekPreviewTime = exoPlayer.currentPosition
                                showSeekIndicator = true
                            } else if (offset.x > screenWidth * 2 / 3) {
                                exoPlayer.seekTo((exoPlayer.currentPosition + 10000).coerceAtMost(duration))
                                seekPreviewTime = exoPlayer.currentPosition
                                showSeekIndicator = true
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
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f)
                .align(Alignment.Center)
        )

        if (!isLocked) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { showBrightnessIndicator = true },
                                onDragEnd = { showBrightnessIndicator = false }
                            ) { change, dragAmount ->
                                change.consume()
                                val delta = -dragAmount.y / size.height
                                brightness = (brightness + delta).coerceIn(0f, 1f)
                            }
                        }
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { showVolumeIndicator = true },
                                onDragEnd = { showVolumeIndicator = false }
                            ) { change, dragAmount ->
                                change.consume()
                                val delta = -dragAmount.y / size.height
                                volume = (volume + delta).coerceIn(0f, 1f)
                                exoPlayer.volume = volume
                            }
                        }
                )
            }
        }

        AnimatedVisibility(
            visible = showBrightnessIndicator,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            GestureIndicator(
                icon = Icons.Default.Brightness6,
                label = "${(brightness * 100).toInt()}%"
            )
        }

        AnimatedVisibility(
            visible = showVolumeIndicator,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            GestureIndicator(
                icon = Icons.Default.VolumeUp,
                label = "${(volume * 100).toInt()}%"
            )
        }

        AnimatedVisibility(
            visible = showSeekIndicator,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            LaunchedEffect(showSeekIndicator) {
                if (showSeekIndicator) {
                    delay(600)
                    showSeekIndicator = false
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xCC000000))
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "${formatTime(seekPreviewTime)} / ${formatTime(duration)}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        AnimatedVisibility(
            visible = showControls && !isLocked,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color(0xCC000000), Color.Transparent)))
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }
                Text(
                    text = videoTitle,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Subtitles, "Subtitles", tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Settings, "Settings", tint = Color.White)
                }
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
                        contentDescription = if (isPlaying) "Pause" else "Play",
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
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000))))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(formatTime(currentPosition), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
                        onValueChange = { value -> exoPlayer.seekTo((value * duration).toLong()) },
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = VibeRed,
                            activeTrackColor = VibeRed,
                            inactiveTrackColor = Color(0x4DFFFFFF)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(formatTime(duration), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
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
                                text = "${playbackSpeed}x",
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

@Composable
private fun GestureIndicator(
    icon: ImageVector,
    label: String
) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xCC000000))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

private fun formatTime(millis: Long): String {
    if (millis <= 0) return "00:00"
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
