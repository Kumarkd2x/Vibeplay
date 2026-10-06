# ==========================================
# Vibe Play - ProGuard Rules
# Ye rules build ke time code ko crash hone se bachate hain
# ==========================================

# ---------- ExoPlayer / Media3 ----------
-keep class androidx.media3.** { *; }
-keep interface androidx.media3.** { *; }
-dontwarn androidx.media3.**

# ---------- Kotlin Coroutines ----------
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# ---------- Retrofit ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# ---------- Gson ----------
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn com.google.gson.**

# ---------- Data Models (Reflection ke liye zaroori) ----------
-keep class com.kumarkd2x.vibeplay.data.model.** { *; }

# ---------- Room Database ----------
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# ---------- DataStore ----------
-keep class androidx.datastore.** { *; }

# ---------- Coil Image Loader ----------
-keep class coil.** { *; }
-dontwarn coil.**

# ---------- Jetpack Compose ----------
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ---------- WebView (YouTube ke liye) ----------
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ---------- General Android ----------
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Native Methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
