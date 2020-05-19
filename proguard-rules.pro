# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/chrrajaslocal/Documents/AndroidSDK/adt-bundle-mac-x86_64-20140624/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings

-keepattributes Signature
-keepattributes Exceptions
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes *Annotation*

# AndroidX
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
# FragmentFactory instantiates fragments using reflection
-keep,allowoptimization class * extends androidx.fragment.app.Fragment

# Kotlin related rules
# Proguard strips out "when mappings"(used then Enums serialized/deserialized) for Kotlin enums, so we keep them.
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keep class kotlin.Metadata {
    public <methods>;
}
-dontwarn org.jetbrains.annotations.**