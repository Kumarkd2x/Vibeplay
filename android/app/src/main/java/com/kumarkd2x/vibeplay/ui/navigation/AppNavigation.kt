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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumarkd2x.vibeplay.ui.screens.HomeScreen
import com.kumarkd2x.vibeplay.ui.screens.LocalScreen
import com.kumarkd2x.vibeplay.ui.screens.SettingsScreen
import com.kumarkd2x.vibeplay.ui.screens.YouTubeScreen
import com.kumarkd2x.vibeplay.ui.theme.TextSecondaryDark
import com.kumarkd2x.vibeplay.ui.theme.VibeBlack
import com.kumarkd2x.vibeplay.ui.theme.VibeDarkSurface
import com.kumarkd2x.vibeplay.ui.theme.VibeRed

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val navItems = listOf(
    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem("Local", Icons.Filled.VideoLibrary, Icons.Outlined.VideoLibrary),
    NavItem("YouTube", Icons.Filled.PlayCircle, Icons.Outlined.PlayCircle),
    NavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
)

@Composable
fun AppNavigation() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = VibeBlack,
        bottomBar = {
            NavigationBar(
                containerColor = VibeDarkSurface,
                contentColor = Color.White
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTab = index },
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(VibeBlack)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(tween(250)) togetherWith fadeOut(tween(250))
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
