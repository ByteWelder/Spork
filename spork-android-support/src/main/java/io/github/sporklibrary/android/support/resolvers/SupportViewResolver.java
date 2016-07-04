package io.github.sporklibrary.android.support.resolvers;

import android.view.View;

import io.github.sporklibrary.annotations.Nullable;

public class SupportViewResolver implements io.github.sporklibrary.interfaces.ViewResolver {

    @Override
    public @Nullable View resolveView(Object object) {
        Class<?> object_class = object.getClass();

        if (android.support.v4.app.Fragment.class.isAssignableFrom(object_class)) {
            return ((android.support.v4.app.Fragment) object).getView();
        } else if (android.support.v7.widget.RecyclerView.ViewHolder.class.isAssignableFrom(object_class)) {
            return ((android.support.v7.widget.RecyclerView.ViewHolder) object).itemView;
        } else {
            return null;
        }
    }
}
