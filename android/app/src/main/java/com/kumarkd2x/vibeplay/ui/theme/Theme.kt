package com.kumarkd2x.vibeplay.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Vibe Play - Theme
 *
 * Ye app ka main theme hai. Isme:
 * - Dark theme (default)
 * - Light theme (optional)
 * - Dynamic color (Android 12+)
 * - User-selectable accent color
 */

// ==================== DARK COLOR SCHEME ====================
private fun vibeDarkColorScheme(
    accent: Color = VibeRed
): androidx.compose.material3.ColorScheme = darkColorScheme(
    // Primary (accent color)
    primary = accent,
    onPrimary = Color.White,
    primaryContainer = accent.copy(alpha = 0.2f),
    onPrimaryContainer = accent,

    // Secondary
    secondary = TextSecondaryDark,
    onSecondary = VibeBlack,
    secondaryContainer = VibeDarkCard,
    onSecondaryContainer = TextPrimaryDark,

    // Tertiary (for special accents)
    tertiary = VibeModeActive,
    onTertiary = VibeBlack,

    // Background
    background = VibeBlack,
    onBackground = TextPrimaryDark,

    // Surface (cards, sheets)
    surface = VibeDarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = VibeDarkCard,
    onSurfaceVariant = TextSecondaryDark,
    surfaceTint = accent,

    // Elevated surfaces
    surfaceContainerLowest = VibeBlack,
    surfaceContainerLow = VibeBlack,
    surfaceContainer = VibeDarkSurface,
    surfaceContainerHigh = VibeDarkCard,
    surfaceContainerHighest = VibeDarkElevated,

    // Error
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRed.copy(alpha = 0.2f),
    onErrorContainer = ErrorRed,

    // Outline
    outline = VibeDarkBorder,
    outlineVariant = VibeDarkBorder.copy(alpha = 0.5f),

    // Inverse
    inverseSurface = VibeWhite,
    inverseOnSurface = VibeBlack,
    inversePrimary = VibeRedDark,

    // Scrim
    scrim = Scrim
)

// ==================== LIGHT COLOR SCHEME ====================
private fun vibeLightColorScheme(
    accent: Color = VibeRed
): androidx.compose.material3.ColorScheme = lightColorScheme(
    primary = accent,
    onPrimary = Color.White,
    primaryContainer = accent.copy(alpha = 0.15f),
    onPrimaryContainer = accent,

    secondary = TextSecondaryLight,
    onSecondary = VibeWhite,
    secondaryContainer = VibeLightCard,
    onSecondaryContainer = TextPrimaryLight,

    tertiary = VibeModeActive,
    onTertiary = VibeWhite,

    background = VibeLightSurface,
    onBackground = TextPrimaryLight,

    surface = VibeLightCard,
    onSurface = TextPrimaryLight,
    surfaceVariant = VibeLightSurface,
    onSurfaceVariant = TextSecondaryLight,
    surfaceTint = accent,

    surfaceContainerLowest = VibeWhite,
    surfaceContainerLow = VibeWhite,
    surfaceContainer = VibeLightSurface,
    surfaceContainerHigh = VibeLightCard,
    surfaceContainerHighest = VibeWhite,

    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRed.copy(alpha = 0.15f),
    onErrorContainer = ErrorRed,

    outline = VibeLightBorder,
    outlineVariant = VibeLightBorder.copy(alpha = 0.5f),

    inverseSurface = VibeBlack,
    inverseOnSurface = VibeWhite,
    inversePrimary = VibeRedLight,

    scrim = Scrim
)

// ==================== MAIN THEME COMPOSABLE ====================
@Composable
fun VibePlayTheme(
    darkTheme: Boolean = true,                 // Default: Dark (OLED)
    accentColor: Color = VibeRed,              // Default: Vibe Red
    dynamicColor: Boolean = false,             // Android 12+ dynamic color
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Agar dynamic color on hai aur Android 12+ hai
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Dark theme with custom accent
        darkTheme -> vibeDarkColorScheme(accentColor)
        // Light theme with custom accent
        else -> vibeLightColorScheme(accentColor)
    }

    // Status bar aur navigation bar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()

            // Status bar icons light/dark
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VibeTypography,
        content = content
    )
}

// ==================== ACCENT COLOR ENUM (User Choice) ====================
enum class VibeAccent(val color: Color, val displayName: String) {
    RED(VibeRed, "Vibe Red"),
    ORANGE(VibeModeActive, "Sunset Orange"),
    PURPLE(DialogueBoostActive, "Royal Purple"),
    BLUE(AccentBlue, "Ocean Blue"),
    GREEN(AccentGreen, "Forest Green"),
    PINK(AccentPink, "Rose Pink"),
    TEAL(AccentTeal, "Teal"),
    GOLD(AccentGold, "Golden")
}

// ==================== PLAYER OVERLAY COLORS ====================
// Player screen me use honge
object PlayerColors {
    val background = PlayerBackground
    val overlay = PlayerOverlay
    val overlayStrong = PlayerOverlayStrong
    val controlBg = PlayerControlBg
    val seekbarBg = PlayerSeekbarBg
    val iconTint = PlayerIconTint
}
