-keep class spork.** { *; }
-keep interface spork.** { *; }
-keep enum spork.** { *; }

-keepclasseswithmembernames class * {
    @spork.** *;
}

-keepclassmembers class ** {
    @spork.** *;
}

-keepclasseswithmembernames class * {
    @javax.inject.* *;
}

-keepclassmembers class ** {
    @javax.inject.* *;
}
