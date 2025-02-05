# Keep Kotlin Metadata
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-keep class kotlin.Metadata { *; }
-keepclassmembers class * {
    @org.jetbrains.annotations.NotNull *;
    @org.jetbrains.annotations.Nullable *;
}
-keepclasseswithmembers class * {
    @kotlin.Metadata <methods>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep Koin DI
-keepnames class com.example.data.di.** { *; }
-keepclassmembers class com.example.data.di.** { *; }
-keep class com.example.data.di.DataModuleKt { *; }

# Keep commons-io library intact
-keep class org.apache.commons.io.** { *; }
-keepclassmembers class org.apache.commons.io.** { *; }
-keepnames class org.apache.commons.io.** { *; }

# Keep metadata
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep the entire inheritance chain for BoundedInputStream
-keep class org.apache.commons.io.input.BoundedInputStream
-keep class java.io.InputStream
-keep class java.io.FilterInputStream
-keep,allowobfuscation @interface *

# Keep the inheritance relationship
-keep class org.apache.commons.io.input.BoundedInputStream {
    *;
}
-keep class * extends org.apache.commons.io.input.BoundedInputStream {
    *;
}
-keep class * extends java.io.FilterInputStream {
    *;
}

# Keep all methods that might contain readAllBytes
-keepclassmembers class * extends java.io.InputStream {
    byte[] readAllBytes();
    int read();
    int read(byte[]);
    int read(byte[], int, int);
    long skip(long);
    int available();
    void close();
    void mark(int);
    void reset();
    boolean markSupported();
}

# Keep all commons-io interfaces
-keep interface org.apache.commons.io.** { *; }

# Keep package and version information
-keeppackagenames org.apache.commons.io.**

# Keep all classes that might use BoundedInputStream
-keep class * implements java.io.InputStream { *; }
-keep class * extends java.io.InputStream { *; } 