package com.kumarkd2x.vibeplay.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumarkd2x.vibeplay.ui.theme.VibeRed
import com.kumarkd2x.vibeplay.ui.theme.VibeTaglineStyle

/**
 * SplashScreen
 *
 * Ye screen app khulte waqt 1.5 second dikhti hai:
 * - Red circle me play button (pulse animation)
 * - "VIBE PLAY" text (fade in)
 * - "Play Your Vibe" tagline
 *
 * Animation:
 * 1. Play button scale hoke aata hai
 * 2. Text fade in hota hai
 * 3. Play button pulse karta rehta hai
 */
@Composable
fun SplashScreen() {

    // Fade in animation (0 → 1)
    var startAnimation by remember { mutableStateOf(false) }
    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "logo_alpha"
    )

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "logo_scale"
    )

    // Pulse animation (infinite)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    // Animation start karo
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    // ==================== UI ====================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0x40E50914),  // Red glow center
                        Color(0x000A0A0A)   // Transparent edges
                    ),
                    radius = 800f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ==================== PLAY BUTTON CIRCLE ====================
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(logoScale * pulseScale)
                    .alpha(logoAlpha * pulseAlpha),
                contentAlignment = Alignment.Center
            ) {
                // Outer glow circle
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(
                            color = VibeRed.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                )

                // Inner solid red circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = VibeRed,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // White play arrow
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ==================== APP NAME ====================
            Text(
                text = "VIBE",
                color = Color.White,
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 8.sp,
                modifier = Modifier
                    .alpha(logoAlpha)
            )

            Text(
                text = "PLAY",
                color = VibeRed,
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 8.sp,
                modifier = Modifier
                    .alpha(logoAlpha)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== TAGLINE ====================
            Text(
                text = "PLAY YOUR VIBE",
                style = VibeTaglineStyle,
                color = Color(0xFFB0B0B0),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(logoAlpha * 0.8f)
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // ==================== LOADING DOTS ====================
            LoadingDots()
        }
    }
}

/**
 * LoadingDots
 *
 * 3 dots jo sequence me bright/dim hote hain.
 * Premium loading indicator.
 */
@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    // Har dot ka alag phase
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dot(alpha = dot1Alpha)
        Dot(alpha = dot2Alpha)
        Dot(alpha = dot3Alpha)
    }
}

@Composable
private fun Dot(alpha: Float) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(
                color = VibeRed.copy(alpha = alpha),
                shape = CircleShape
            )
    )
}
