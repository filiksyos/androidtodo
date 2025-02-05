# Keep Commons IO classes with their metadata
-keep class org.apache.commons.io.** { *; }
-keepclassmembers class org.apache.commons.io.** { *; }
-keepattributes Signature,*Annotation*,EnclosingMethod,SourceFile,LineNumberTable

# Keep Commons IO methods
-keepclassmembers class org.apache.commons.io.IOUtils {
    public static <methods>;
}

# Keep package info and metadata
-keep class org.apache.commons.io.IOUtils { *; }
-keep class org.apache.commons.io.** { *** get*(); }
-keep class org.apache.commons.io.** { *** set*(...); }

# Keep manifest entries
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes *Annotation*,EnclosingMethod,Signature

# Keep all native & interfaces for Commons IO
-keepclasseswithmembernames,includedescriptorclasses class org.apache.commons.io.** {
    native <methods>;
}

# Keep META-INF and resource files
-keep,includedescriptorclasses class org.apache.commons.io.** { *; }
-dontwarn org.apache.commons.io.** 