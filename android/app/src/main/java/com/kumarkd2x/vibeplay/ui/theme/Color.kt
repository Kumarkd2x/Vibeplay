package com.kumarkd2x.vibeplay.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Vibe Play - Color Palette
 *
 * Ye file saari colors ko Compose ke liye define karti hai.
 * Har color ka naam "Vibe" prefix se shuru hota hai taaki conflict na ho.
 */

// ==================== BRAND COLORS ====================
val VibeRed = Color(0xFFE50914)         // Main red accent
val VibeRedDark = Color(0xFFB20710)     // Darker red (pressed state)
val VibeRedLight = Color(0xFFFF1F2A)    // Lighter red (hover state)

// ==================== DARK THEME ====================
val VibeBlack = Color(0xFF0A0A0A)         // Pure black background (OLED)
val VibeDarkSurface = Color(0xFF141414)   // Surface (cards)
val VibeDarkCard = Color(0xFF1C1C1C)      // Cards
val VibeDarkBorder = Color(0xFF2A2A2A)    // Borders / dividers
val VibeDarkElevated = Color(0xFF222222)  // Elevated surfaces (dialogs)

// ==================== LIGHT THEME ====================
val VibeWhite = Color(0xFFFFFFFF)
val VibeLightSurface = Color(0xFFF5F5F5)
val VibeLightCard = Color(0xFFFFFFFF)
val VibeLightBorder = Color(0xFFE0E0E0)

// ==================== TEXT COLORS ====================
val TextPrimaryDark = Color(0xFFFFFFFF)
val TextSecondaryDark = Color(0xFFB0B0B0)
val TextTertiaryDark = Color(0xFF6E6E6E)
val TextPrimaryLight = Color(0xFF0A0A0A)
val TextSecondaryLight = Color(0xFF5A5A5A)

// ==================== STATUS COLORS ====================
val SuccessGreen = Color(0xFF4CAF50)
val WarningYellow = Color(0xFFFFC107)
val ErrorRed = Color(0xFFF44336)
val InfoBlue = Color(0xFF2196F3)

// ==================== PLAYER COLORS ====================
val PlayerBackground = Color(0xFF000000)
val PlayerOverlay = Color(0x80000000)          // 50% transparent black
val PlayerOverlayStrong = Color(0xCC000000)    // 80% transparent black
val PlayerControlBg = Color(0x1F000000)
val PlayerSeekbarBg = Color(0x4DFFFFFF)
val PlayerIconTint = Color(0xFFFFFFFF)

// ==================== VIBE UNIQUE COLORS ====================
val VibeModeActive = Color(0xFFFF6B35)      // Vibe Mode ON (orange)
val DialogueBoostActive = Color(0xFF9C27B0) // Dialogue Boost ON (purple)
val MovieModeActive = Color(0xFFFFB800)     // Movie Mode ON (gold)
val SecureFolderGold = Color(0xFFFFD700)    // Secure Folder (gold)

// ==================== ACCENT PRESETS (User Choice) ====================
val AccentRed = Color(0xFFE50914)
val AccentOrange = Color(0xFFFF6B35)
val AccentPurple = Color(0xFF9C27B0)
val AccentBlue = Color(0xFF2196F3)
val AccentGreen = Color(0xFF4CAF50)
val AccentPink = Color(0xFFE91E63)
val AccentTeal = Color(0xFF009688)
val AccentGold = Color(0xFFFFD700)

// ==================== GRADIENTS ====================
val GradientStart = Color(0xFFE50914)
val GradientEnd = Color(0xFFB20710)

// ==================== TRANSPARENT ====================
val Transparent = Color(0x00000000)
val SemiTransparentBlack = Color(0x99000000)
val Scrim = Color(0xB3000000)

// ==================== SHIMMER (Loading animation) ====================
val ShimmerBase = Color(0xFF1C1C1C)
val ShimmerHighlight = Color(0xFF2A2A2A)
