package io.github.sporklibrary.utils.support;

import android.support.v4.app.Fragment;

import io.github.sporklibrary.annotations.Nullable;

/**
 * Provides support methods for V4 fragments
 */
public final class SupportFragments {

    @Nullable
    private static Class<?> sSupportFragmentClass;

    static {
        try {
            sSupportFragmentClass = Fragment.class;
        } catch (NoClassDefFoundError caught) {
            sSupportFragmentClass = null;
        }
    }

    private SupportFragments() {
    }

    public static boolean isFragmentClass(Class<?> classObject) {
        return sSupportFragmentClass != null && sSupportFragmentClass.isAssignableFrom(classObject);
    }
}
