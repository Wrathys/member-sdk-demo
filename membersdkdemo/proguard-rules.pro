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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

-keep, allowobfuscation class com.companycom.satayupomsri.membersdkdemo.*
-keepclassmembers, allowobfuscation class * {
    *;
}

-keepnames class com.company.MyClasscom.satayupomsri.membersdkdemo.MemberSignInButton
-keepclassmembernames class com.satayupomsri.membersdkdemo.MemberSignInButton {
    public <methods>;
    public <fields>;
    #!private *; also tried this but it didn't work
}

-keepnames class com.company.MyClasscom.satayupomsri.membersdkdemo.MemberSignInListener
-keepclassmembernames class com.satayupomsri.membersdkdemo.MemberSignInListener {
    public <methods>;
    public <fields>;
    #!private *; also tried this but it didn't work
}