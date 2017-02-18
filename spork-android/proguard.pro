-keep class spork.** { *; }
-keep interface spork.** { *; }
-keep enum spork.** {
    **[] $VALUES;
    public *;
}

-keepattributes *RuntimeVisibleAnnotations*