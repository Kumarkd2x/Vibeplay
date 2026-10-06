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
    var bgPlay by remember { mutableStateOf(true) }
    var pip by remember { mutableStateOf(true) }
    var haptic by remember { mutableStateOf(true) }
    var doubleTap by remember { mutableStateOf(true) }
    var swipe by remember { mutableStateOf(true) }
    var dialogue by remember { mutableStateOf(false) }
    var volBoost by remember { mutableStateOf(false) }
    var audioFocus by remember { mutableStateOf(true) }
    var autoSub by remember { mutableStateOf(false) }
    var secure by remember { mutableStateOf(false) }
    var blacklist by remember { mutableStateOf(false) }
    var vibeMode by remember { mutableStateOf(true) }
    var proxy by remember { mutableStateOf(false) }
    var hwDecoder by remember { mutableStateOf(true) }
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
            Section("Appearance", Icons.Default.Palette) {
                SwitchItem(Icons.Default.DarkMode, "Dark Theme", "Pure black OLED-friendly", darkTheme) { darkTheme = it }
                SwitchItem(Icons.Default.ColorLens, "Material You", "Android 12+ dynamic colors", materialYou) { materialYou = it }
                ClickItem(Icons.Default.Brightness6, "Accent Color", "Current: Vibe Red") { }
            }
        }

        item {
            Section("Playback", Icons.Default.PlayCircle) {
                SwitchItem(Icons.Default.PlayCircle, "Autoplay", "Auto play next video", autoplay) { autoplay = it }
                SwitchItem(Icons.Default.Memory, "Background Play", "Play with screen off", bgPlay) { bgPlay = it }
                SwitchItem(Icons.Default.HighQuality, "PiP Mode", "Floating mini player", pip) { pip = it }
                ClickItem(Icons.Default.Speed, "Default Speed", "1.0x") { }
                SwitchItem(Icons.Default.Movie, "Movie Mode", "Enhanced dark scenes", movieMode) { movieMode = it }
            }
        }

        item {
            Section("Gestures", Icons.Default.Gesture) {
                SwitchItem(Icons.Default.Gesture, "Double Tap Seek", "10s forward/backward", doubleTap) { doubleTap = it }
                SwitchItem(Icons.Default.Brightness6, "Swipe Gestures", "Brightness, Volume, Seek", swipe) { swipe = it }
                SwitchItem(Icons.Default.Timer, "Haptic Feedback", "Vibration on tap", haptic) { haptic = it }
                ClickItem(Icons.Default.Gesture, "Player Layout", "Customize buttons") { }
            }
        }

        item {
            Section("Audio", Icons.Default.GraphicEq) {
                SwitchItem(Icons.Default.VolumeUp, "Dialogue Boost", "Enhance speech clarity", dialogue) { dialogue = it }
                SwitchItem(Icons.Default.VolumeUp, "Volume Boost", "Up to 200% loudness", volBoost) { volBoost = it }
                SwitchItem(Icons.Default.GraphicEq, "Audio Focus", "Pause when headphones out", audioFocus) { audioFocus = it }
            }
        }

        item {
            Section("Subtitles", Icons.Default.Subtitles) {
                SwitchItem(Icons.Default.Subtitles, "Auto-Load Subs", "Load .srt from folder", autoSub) { autoSub = it }
                ClickItem(Icons.Default.Translate, "Language", "English") { }
                ClickItem(Icons.Default.Language, "Subtitle Style", "Font, size, background") { }
            }
        }

        item {
            Section("Storage", Icons.Default.Folder) {
                SwitchItem(Icons.Default.Lock, "Secure Folder", "PIN-protected videos", secure) { secure = it }
                SwitchItem(Icons.Default.Folder, "Folder Blacklist", "Hide folders", blacklist) { blacklist = it }
                ClickItem(Icons.Default.Folder, "Clear Cache", "Free storage space") { }
            }
        }

        item {
            Section("Network", Icons.Default.NetworkCheck) {
                SwitchItem(Icons.Default.Speed, "Vibe Mode", "Auto-quality by network", vibeMode) { vibeMode = it }
                SwitchItem(Icons.Default.Security, "Proxy", "HTTP/SOCKS5 routing", proxy) { proxy = it }
                ClickItem(Icons.Default.HighQuality, "Default Quality", "Auto (up to 4K)") { }
            }
        }

        item {
            Section("Advanced", Icons.Default.Memory) {
                SwitchItem(Icons.Default.Memory, "HW Decoder", "Use GPU for smooth playback", hwDecoder) { hwDecoder = it }
                ClickItem(Icons.Default.Speed, "Buffer Size", "Auto") { }
                ClickItem(Icons.Default.Memory, "Reset Settings", "Restore defaults") { }
            }
        }

        item {
            Section("About", Icons.Default.Info) {
                ClickItem(Icons.Default.Info, "Version", "1.0.0 (Build 1)") { }
                ClickItem(Icons.Default.Info, "Vibe Play", "Play Your Vibe") { }
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
private fun Section(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        ) {
            Icon(icon, null, tint = VibeRed, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, color = VibeRed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
private fun SwitchItem(
    icon: ImageVector,
    title: String,
    sub: String,
    checked: Boolean,
    onCheck: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheck(!checked) }
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
                icon, null,
                tint = if (checked) VibeRed else Color(0xFFB0B0B0),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(sub, color = Color(0xFF8E8E8E), fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheck,
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
private fun ClickItem(icon: ImageVector, title: String, sub: String, onClick: () -> Unit) {
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
            Icon(icon, null, tint = Color(0xFFB0B0B0), modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(sub, color = Color(0xFF8E8E8E), fontSize = 12.sp)
        }
        Icon(Icons.Default.PlayCircle, null, tint = Color(0xFF4E4E4E), modifier = Modifier.size(18.dp))
    }
}
