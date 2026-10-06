# 🎬 Vibe Play

> **Play Your Vibe** — A sleek, pure Native Kotlin + Jetpack Compose video player with ExoPlayer (Media3). Dark theme, red accents, zero lag.

[![Build Vibe Play APK](https://github.com/Kumarkd2x/vibe-play/actions/workflows/build-apk.yml/badge.svg)](https://github.com/Kumarkd2x/vibe-play/actions/workflows/build-apk.yml)

---

## ✨ Features

### 🎥 Video Playback
- **Local Offline Player** — Powered by ExoPlayer (Media3), 4K support
- **Ad-Free YouTube Streaming** — Native UI, no WebView
- **Picture-in-Picture** — Background play with floating mini player
- **Sponsors Auto-Skip** — Integrated SponsorBlock support (coming soon)

### 🎮 Advanced Controls (mpvRx-style)
- **Custom Player Layout** — User apne buttons set kare
- **Gesture Controls** — Double-tap seek, swipe brightness/volume, pinch zoom
- **Playback Speed** — 0.25x to 4x
- **Aspect Ratio Cycle** — Fit, Fill, Zoom, 16:9, 4:3
- **Lock Screen** — Buttons hide, sirf unlock dikhe
- **Screen Rotation** — Auto + Manual
- **Sleep Timer** — Auto-stop after X minutes

### 🎨 Appearance
- **Material 3 Design** — Modern, minimal, clean
- **Pure Black OLED Theme** — Battery saving
- **8 Accent Colors** — Red, Orange, Purple, Blue, Green, Pink, Teal, Gold
- **Material You** — Android 12+ dynamic colors
- **10+ Custom Themes** — (coming soon)

### 🎵 Audio
- **Dialogue Boost** — Speech clarity enhancement
- **Volume Boost** — Up to 200%
- **Audio Focus** — Auto-pause when headphones removed
- **Background Play** — Screen off pe bhi audio chalta hai

### 📝 Subtitles
- **Auto-Load** — .srt file same folder me ho toh auto-load
- **Multi-Language** — Subtitle track switch
- **Customizable Style** — Font, size, background, position
- **Sync Adjust** — Manual offset correction

### 🔒 Privacy & Security
- **Secure Folder** — PIN-protected private videos
- **Folder Blacklist** — Hide folders from library
- **No Ads** — YouTube bhi ad-free
- **No Tracking** — Sab data local
- **No Login Required** — Google account ki zaroorat nahi

### 🌟 Vibe Play Unique
- **Vibe Mode** — Network ke hisaab se auto-quality
- **Movie Mode** — Enhanced colors for dark scenes
- **Smart Bookmarks** — Timestamp pe jump
- **Watch Stats** — Kitna time spend kiya
- **Haptic Feedback** — Premium vibration feel

---

## 📱 Download

Latest APKs are available in the [Releases](https://github.com/Kumarkd2x/vibe-play/releases) section.

Choose the right APK for your device:

| APK | Best For |
|---|---|
| `app-arm64-v8a-release.apk` | **Modern phones (99%)** — Recommended |
| `app-armeabi-v7a-release.apk` | Older phones (pre-2016) |
| `app-x86-release.apk` | Emulators (32-bit) |
| `app-x86_64-release.apk` | Emulators (64-bit) |
| `app-universal-release.apk` | Works on **all** devices |

**Minimum Requirements:** Android 7.0 (API 24) or higher

---

## 🛠️ Built With

- **Language:** 100% Kotlin
- **UI:** Jetpack Compose + Material 3
- **Player Engine:** ExoPlayer (AndroidX Media3)
- **Architecture:** MVVM + Repository Pattern
- **Async:** Kotlin Coroutines + Flow
- **Networking:** Retrofit + OkHttp
- **Image Loading:** Coil
- **Database:** Room (coming soon)
- **Settings:** DataStore Preferences (coming soon)
- **API:** Piped (open-source YouTube API)

---

## 🚀 Build From Source

### Prerequisites
- JDK 17
- Android SDK 34
- Gradle 8.7

### Steps
```bash
git clone https://github.com/Kumarkd2x/vibe-play.git
cd vibe-play/android
./gradlew assembleRelease
