# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn com.satayupomsri.membersdkdemo.status.*
-keepclasseswithmembers class com.satayupomsri.membersdkdemo.status.*
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.satayupomsri.membersdkdemo.MemberSignInButton
-keep class com.satayupomsri.membersdkdemo.MemberSignInButton { *; }

-dontwarn com.satayupomsri.membersdkdemo.MemberSignInListener
-keep interface com.satayupomsri.membersdkdemo.MemberSignInListener { *; }

-dontwarn com.satayupomsri.membersdkdemo.MemberSignInClient
-keep class com.satayupomsri.membersdkdemo.MemberSignInClient { *; }