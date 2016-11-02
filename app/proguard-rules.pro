# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\huanjinzi\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

-keep public class com.campus.huanjinzi.campusmvp.swuwifi.LoginBean
-keep public class com.campus.huanjinzi.campusmvp.swuwifi.LogoutBean
-keep public class com.campus.huanjinzi.campusmvp.swuwifi.SwuServiceBean

-keep public class com.campus.huanjinzi.campusmvp.data.StudentInfo
-keep public class com.campus.huanjinzi.campusmvp.data.StudentCj
-keep public class com.campus.huanjinzi.campusmvp.data.ResponseBean
-keep public class com.campus.huanjinzi.campusmvp.swuwifi.SwuServiceBean
-keep public class com.campus.huanjinzi.campusmvp.TaskManager



