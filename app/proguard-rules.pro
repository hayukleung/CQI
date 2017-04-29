# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hayukleung/Development/development_android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# okhttp3
-dontwarn okhttp3.**

-dontwarn **

-keep class com.xfzbd.cqi.model.BaseBean
-keepclassmembers class com.xfzbd.cqi.model.BaseBean { *; }
-keep class * extends com.xfzbd.cqi.model.BaseBean
-keepclassmembers class * extends com.xfzbd.cqi.model.BaseBean { *; }

# gradle-retrolambda (https://github.com/evant/gradle-retrolambda)
-dontwarn java.lang.invoke.*

# sqlcipher
-keep class net.sqlcipher.** { *; }

### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties

# mina
-keep class org.apache.mina.** { *; }
