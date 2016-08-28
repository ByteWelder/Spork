# The SporkAndroid class is required for automatic registration by the Spork core library
-keep class io.github.sporklibrary.android.SporkAndroid { *; }
-keep public class io.github.sporklibrary.** { *; }
-keep public interface io.github.sporklibrary.** { *; }
-keep public enum io.github.sporklibrary.** {
    **[] $VALUES;
    public *;
}