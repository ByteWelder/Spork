package io.github.sporklibrary.android.support.resolvers;

import android.content.Context;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.android.resolvers.ContextResolver;

public class SupportContextResolver implements ContextResolver {

    @Override
    public @Nullable Context resolveContext(Object object) {
        Class<?> object_class = object.getClass();

        if (android.support.v4.app.Fragment.class.isAssignableFrom(object_class)) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }
}
