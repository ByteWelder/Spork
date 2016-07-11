package io.github.sporklibrary.android.support.resolvers;

import android.content.Context;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.android.interfaces.ContextResolver;

public class SupportContextResolver implements ContextResolver {

    @Override
    public @Nullable Context resolveContext(Object object) {
        Class<?> object_class = object.getClass();

        if (android.support.v4.app.Fragment.class.isAssignableFrom(object_class)) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (android.support.v7.widget.RecyclerView.ViewHolder.class.isAssignableFrom(object_class)) {
            return ((android.support.v7.widget.RecyclerView.ViewHolder) object).itemView.getContext();
        } else {
            return null;
        }
    }
}
