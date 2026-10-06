package com.kumarkd2x.vibeplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumarkd2x.vibeplay.ui.theme.VibeDarkCard
import com.kumarkd2x.vibeplay.ui.theme.VibeRed

@Composable
fun SettingsScreen() {
    var darkTheme by remember { mutableStateOf(true) }
    var materialYou by remember { mutableStateOf(false) }
    var autoplay by remember { mutableStateOf(true) }
    var backgroundPlay by remember { mutableStateOf(true) }
    var pipMode by remember { mutableStateOf(true) }
    var hapticFeedback by remember { mutableStateOf(true) }
    var doubleTapSeek by remember { mutableStateOf(true) }
    var swipeGestures by remember { mutableStateOf(true) }
    var dialogueBoost by remember { mutableStateOf(false) }
    var volumeBoost by remember { mutableStateOf(false) }
    var audioFocus by remember { mutableStateOf(true) }
    var autoSubtitles by remember { mutableStateOf(false) }
    var secureFolder by remember { mutableStateOf(false) }
    var folderBlacklist by remember { mutableStateOf(false) }
    var vibeMode by remember { mutableStateOf(true) }
    var proxyEnabled by remember { mutableStateOf(false) }
    var hardwareDecoder by remember { mutableStateOf(true) }
    var movieMode by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A)),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Settings", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Customize Vibe Play your way", color = Color(0xFFB0B0B0), fontSize = 13.sp)
            }
        }

        item {
            SettingsSection(title = "Appearance", icon = Icons.Default.Palette) {
                SettingsSwitchItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Theme",
                    subtitle = "Pure black OLED-friendly",
                    checked = darkTheme,
                    onCheckedChange = { darkTheme = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.ColorLens,
                    title = "Material You",
                    subtitle = "Android 12+ dynamic colors",
                    checked = materialYou,
                    onCheckedChange = { materialYou = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Brightness6,
                    title = "Accent Color",
                    subtitle = "Current: Vibe Red",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "Playback", icon = Icons.Default.PlayCircle) {
                SettingsSwitchItem(
                    icon = Icons.Default.PlayCircle,
                    title = "Autoplay",
                    subtitle = "Auto play next video",
                    checked = autoplay,
                    onCheckedChange = { autoplay = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Memory,
                    title = "Background Play",
                    subtitle = "Play with screen off",
                    checked = backgroundPlay,
                    onCheckedChange = { backgroundPlay = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.HighQuality,
                    title = "Picture-in-Picture",
                    subtitle = "Floating mini player",
                    checked = pipMode,
                    onCheckedChange = { pipMode = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Speed,
                    title = "Default Playback Speed",
                    subtitle = "1.0x",
                    onClick = { }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Movie,
                    title = "Movie Mode",
                    subtitle = "Enhanced colors for dark scenes",
                    checked = movieMode,
                    onCheckedChange = { movieMode = it }
                )
            }
        }

        item {
            SettingsSection(title = "Gestures & Controls", icon = Icons.Default.Gesture) {
                SettingsSwitchItem(
                    icon = Icons.Default.Gesture,
                    title = "Double Tap to Seek",
                    subtitle = "10s forward/backward",
                    checked = doubleTapSeek,
                    onCheckedChange = { doubleTapSeek = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Brightness6,
                    title = "Swipe Gestures",
                    subtitle = "Brightness, Volume, Seek",
                    checked = swipeGestures,
                    onCheckedChange = { swipeGestures = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Timer,
                    title = "Haptic Feedback",
                    subtitle = "Vibration on button tap",
                    checked = hapticFeedback,
                    onCheckedChange = { hapticFeedback = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Gesture,
                    title = "Player Layout",
                    subtitle = "Customize buttons order",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "Audio", icon = Icons.Default.GraphicEq) {
                SettingsSwitchItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Dialogue Boost",
                    subtitle = "Enhance speech clarity",
                    checked = dialogueBoost,
                    onCheckedChange = { dialogueBoost = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Volume Boost",
                    subtitle = "Up to 200% loudness",
                    checked = volumeBoost,
                    onCheckedChange = { volumeBoost = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.GraphicEq,
                    title = "Audio Focus",
                    subtitle = "Pause when headphones removed",
                    checked = audioFocus,
                    onCheckedChange = { audioFocus = it }
                )
            }
        }

        item {
            SettingsSection(title = "Subtitles", icon = Icons.Default.Subtitles) {
                SettingsSwitchItem(
                    icon = Icons.Default.Subtitles,
                    title = "Auto-Load Subtitles",
                    subtitle = "Load .srt from same folder",
                    checked = autoSubtitles,
                    onCheckedChange = { autoSubtitles = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Translate,
                    title = "Preferred Language",
                    subtitle = "English",
                    onClick = { }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Language,
                    title = "Subtitle Style",
                    subtitle = "Font, size, background",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "Storage", icon = Icons.Default.Folder) {
                SettingsSwitchItem(
                    icon = Icons.Default.Lock,
                    title = "Secure Folder",
                    subtitle = "PIN-protected private videos",
                    checked = secureFolder,
                    onCheckedChange = { secureFolder = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Folder,
                    title = "Folder Blacklist",
                    subtitle = "Hide folders from library",
                    checked = folderBlacklist,
                    onCheckedChange = { folderBlacklist = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Folder,
                    title = "Clear Cache",
                    subtitle = "Free up storage space",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "Network", icon = Icons.Default.NetworkCheck) {
                SettingsSwitchItem(
                    icon = Icons.Default.Speed,
                    title = "Vibe Mode",
                    subtitle = "Auto-quality based on network",
                    checked = vibeMode,
                    onCheckedChange = { vibeMode = it }
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Security,
                    title = "Proxy",
                    subtitle = "Route traffic through HTTP/SOCKS5",
                    checked = proxyEnabled,
                    onCheckedChange = { proxyEnabled = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.HighQuality,
                    title = "Default Quality",
                    subtitle = "Auto (up to 4K)",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "Advanced", icon = Icons.Default.Memory) {
                SettingsSwitchItem(
                    icon = Icons.Default.Memory,
                    title = "Hardware Decoder",
                    subtitle = "Use GPU for smoother playback",
                    checked = hardwareDecoder,
                    onCheckedChange = { hardwareDecoder = it }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Speed,
                    title = "Buffer Size",
                    subtitle = "Auto (based on RAM)",
                    onClick = { }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Memory,
                    title = "Reset Settings",
                    subtitle = "Restore defaults",
                    onClick = { }
                )
            }
        }

        item {
            SettingsSection(title = "About", icon = Icons.Default.Info) {
                SettingsClickableItem(
                    icon = Icons.Default.Info,
                    title = "Version",
                    subtitle = "1.0.0 (Build 1)",
                    onClick = { }
                )
                SettingsClickableItem(
                    icon = Icons.Default.Info,
                    title = "Vibe Play",
                    subtitle = "Play Your Vibe",
                    onClick = { }
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Made with love by Kumarkd2x", color = Color(0xFF6E6E6E), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Vibe Play - v1.0.0", color = Color(0xFF6E6E6E), fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = VibeRed, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = VibeRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = VibeDarkCard)
        ) {
            Column { content() }
        }
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF252525)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) VibeRed else Color(0xFFB0B0B0),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF8E8E8E), fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = VibeRed,
                uncheckedThumbColor = Color(0xFFB0B0B0),
                uncheckedTrackColor = Color(0xFF3A3A3A),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF252525)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFB0B0B0),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF8E8E8E), fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Default.PlayCircle,
            contentDescription = null,
            tint = Color(0xFF4E4E4E),
            modifier = Modifier.size(18.dp)
        )
    }
}
