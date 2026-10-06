package com.kumarkd2x.vibeplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumarkd2x.vibeplay.ui.theme.SectionHeaderStyle
import com.kumarkd2x.vibeplay.ui.theme.VibeDarkCard
import com.kumarkd2x.vibeplay.ui.theme.VibeLogoStyle
import com.kumarkd2x.vibeplay.ui.theme.VibeRed
import com.kumarkd2x.vibeplay.ui.theme.VibeRedDark

/**
 * HomeScreen
 *
 * Vibe Play ka main home screen. Isme:
 * - Bada "VIBE PLAY" logo
 * - 2 bade cards: Play Local Videos, Stream YouTube
 * - "Quick Access" section me chhote cards
 * - Bottom nav ke saath integrate
 *
 * @param onNavigateToTab - Bottom nav tab change karne ke liye callback
 *                          (0=Home, 1=Local, 2=YouTube, 3=Settings)
 */
@Composable
fun HomeScreen(
    onNavigateToTab: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // ==================== HEADER ====================
        Text(
            text = "VIBE",
            style = VibeLogoStyle,
            color = Color.White
        )
        Text(
            text = "PLAY",
            style = VibeLogoStyle,
            color = VibeRed
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Play Your Vibe",
            color = Color(0xFFB0B0B0),
            fontSize = 14.sp,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ==================== MAIN CARDS ====================

        // Card 1: Play Local Videos
        BigActionCard(
            title = "Play Local Videos",
            subtitle = "Offline playback, no internet needed",
            icon = Icons.Default.VideoLibrary,
            gradientColors = listOf(VibeRed, VibeRedDark),
            onClick = { onNavigateToTab(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card 2: Stream YouTube
        BigActionCard(
            title = "Stream YouTube",
            subtitle = "Ad-free, background play supported",
            icon = Icons.Default.PlayCircle,
            gradientColors = listOf(Color(0xFFFF1F2A), VibeRed),
            onClick = { onNavigateToTab(2) }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ==================== QUICK ACCESS ====================
        Text(
            text = "Quick Access",
            style = SectionHeaderStyle,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickAccessCard(
                title = "Trending",
                icon = Icons.Default.Whatshot,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(2) }
            )
            QuickAccessCard(
                title = "Recent",
                icon = Icons.Default.VideoLibrary,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(1) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickAccessCard(
                title = "Favorites",
                icon = Icons.Default.PlayCircle,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(1) }
            )
            QuickAccessCard(
                title = "Settings",
                icon = Icons.Default.ArrowForward,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(3) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ==================== VIBE TIP ====================
        VibeTipCard()

        Spacer(modifier = Modifier.height(100.dp)) // Bottom nav ke liye space
    }
}

// ==================== BIG ACTION CARD ====================
@Composable
private fun BigActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(gradientColors)
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon circle
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// ==================== QUICK ACCESS CARD ====================
@Composable
private fun QuickAccessCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VibeDarkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = VibeRed,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ==================== VIBE TIP CARD ====================
@Composable
private fun VibeTipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VibeDarkCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(VibeRed.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "💡",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Pro Tip",
                    color = VibeRed,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Long-press any video for options. Double-tap player to seek 10s.",
                    color = Color(0xFFB0B0B0),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
