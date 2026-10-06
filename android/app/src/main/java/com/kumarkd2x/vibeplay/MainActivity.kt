package com.kumarkd2x.vibeplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import com.kumarkd2x.vibeplay.ui.navigation.AppNavigation
import com.kumarkd2x.vibeplay.ui.screens.SplashScreen
import com.kumarkd2x.vibeplay.ui.theme.VibePlayTheme
import com.kumarkd2x.vibeplay.ui.theme.VibeRed

/**
 * MainActivity - Vibe Play ka entry point
 *
 * Ye activity 3 kaam karti hai:
 * 1. Splash screen dikhana (1.5 sec)
 * 2. Theme apply karna (dark + red accent)
 * 3. Pura navigation setup karna
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge display enable karo (modern Android look)
        enableEdgeToEdge()

        setContent {
            VibePlayTheme(
                darkTheme = true,        // Default: Dark theme
                accentColor = VibeRed,   // Default: Vibe Red accent
                dynamicColor = false     // Material You off by default
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VibePlayRoot()
                }
            }
        }
    }
}

/**
 * VibePlayRoot
 *
 * Ye composable decide karta hai ki splash dikhana hai ya main app.
 * Flow:
 * - Pehle 1.5 sec splash screen
 * - Phir fade out hoke main navigation
 */
@androidx.compose.runtime.Composable
fun VibePlayRoot() {
    var showSplash by remember { mutableStateOf(true) }

    // Splash screen ko 1.5 second dikhao
    LaunchedEffect(Unit) {
        delay(1500)
        showSplash = false
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0A))) {

        // ==================== SPLASH SCREEN ====================
        AnimatedVisibility(
            visible = showSplash,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SplashScreen()
        }

        // ==================== MAIN APP ====================
        AnimatedVisibility(
            visible = !showSplash,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AppNavigation()
        }
    }
}
