package com.kumarkd2x.vibeplay.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kumarkd2x.vibeplay.ui.screens.HomeScreen
import com.kumarkd2x.vibeplay.ui.screens.LocalScreen
import com.kumarkd2x.vibeplay.ui.screens.SettingsScreen
import com.kumarkd2x.vibeplay.ui.screens.YouTubeScreen
import com.kumarkd2x.vibeplay.ui.theme.VibeBlack
import com.kumarkd2x.vibeplay.ui.theme.VibeDarkSurface
import com.kumarkd2x.vibeplay.ui.theme.VibeRed
import com.kumarkd2x.vibeplay.ui.theme.TextSecondaryDark

/**
 * AppNavigation
 *
 * Ye pura app ka navigation handle karta hai:
 * - Bottom Navigation Bar (4 tabs)
 * - Screen switching (Home, Local, YouTube, Settings)
 * - Smooth fade animations between screens
 */

// ==================== NAVIGATION ITEMS ====================
data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val navItems = listOf(
    NavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    NavItem(
        title = "Local",
        selectedIcon = Icons.Filled.VideoLibrary,
        unselectedIcon = Icons.Outlined.VideoLibrary
    ),
    NavItem(
        title = "YouTube",
        selectedIcon = Icons.Filled.PlayCircle,
        unselectedIcon = Icons.Outlined.PlayCircle
    ),
    NavItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

// ==================== MAIN NAVIGATION ====================
@Composable
fun AppNavigation() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = VibeBlack,
        bottomBar = {
            VibeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(VibeBlack)
        ) {
            // Screen switch with fade animation
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith
                            fadeOut(animationSpec = tween(250))
                },
                label = "screen_transition"
            ) { tab ->
                when (tab) {
                    0 -> HomeScreen(onNavigateToTab = { selectedTab = it })
                    1 -> LocalScreen()
                    2 -> YouTubeScreen()
                    3 -> SettingsScreen()
                }
            }
        }
    }
}

// ==================== BOTTOM NAVIGATION BAR ====================
@Composable
fun VibeBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = VibeDarkSurface,
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedTab == index

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if (isSelected) VibeRed else TextSecondaryDark
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) VibeRed else TextSecondaryDark,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VibeRed,
                    selectedTextColor = VibeRed,
                    unselectedIconColor = TextSecondaryDark,
                    unselectedTextColor = TextSecondaryDark,
                    indicatorColor = VibeRed.copy(alpha = 0.15f)
                )
            )
        }
    }
}

// Padding import (missing)
private val Int.dp get() = androidx.compose.ui.unit.Dp(this.toFloat())
