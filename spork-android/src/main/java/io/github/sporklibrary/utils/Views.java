package io.github.sporklibrary.utils;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.utils.support.SupportLibraries;

public final class Views {

    private Views() {
    }

    /**
     * @param object a View, Fragment Activity or Support Fragment
     * @return the root view from any regular/support Activity/Fragment/View
     */
    @Nullable
    public static View getRootView(Object object) {
        Class<?> object_class = object.getClass();

        if (View.class.isAssignableFrom(object_class)) {
            return (View) object;
        } else if (Activity.class.isAssignableFrom(object_class)) {
            return ((Activity) object).getWindow().getDecorView();
        } else if (Fragment.class.isAssignableFrom(object_class)) {
            return ((Fragment) object).getView();
        } else if (SupportLibraries.hasSupportV4() && android.support.v4.app.Fragment.class.isAssignableFrom(object_class)) {
            return ((android.support.v4.app.Fragment) object).getView();
        } else if (SupportLibraries.hasRecyclerViewV7() && android.support.v7.widget.RecyclerView.ViewHolder.class.isAssignableFrom(object_class)) {
            return ((android.support.v7.widget.RecyclerView.ViewHolder) object).itemView;
        } else {
            return null;
        }
    }
}
