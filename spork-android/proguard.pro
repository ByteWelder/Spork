-keep class io.github.sporklibrary.** { *; }
-keep interface io.github.sporklibrary.** { *; }
-keep enum io.github.sporklibrary.** {
    **[] $VALUES;
    public *;
}

-keepattributes *RuntimeVisibleAnnotations*