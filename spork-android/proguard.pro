-keep class spork.** { *; }
-keep interface spork.** { *; }
-keep enum spork.** { *; }

-keepclasseswithmembernames class * {
    @spork.* <fields>;
}

-keepclasseswithmembernames class * {
    @spork.* <methods>;
}
