package io.github.sporklibrary.android.binders;

import android.view.View;

import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.internal.reflection.AnnotatedMethod;
import io.github.sporklibrary.internal.reflection.AnnotatedMethods;
import io.github.sporklibrary.android.utils.Views;

public class BindClickBinder implements MethodBinder<BindClick> {

    private class OnClickListener implements View.OnClickListener {
        private final AnnotatedMethod mAnnotatedMethod;
        private final Object mObject;

        public OnClickListener(AnnotatedMethod annotatedMethod, Object object) {
            mAnnotatedMethod = annotatedMethod;
            mObject = object;
        }

        @Override
        public void onClick(View v) {
            Class<?>[] parameter_types = mAnnotatedMethod.getMethod().getParameterTypes();

            if (parameter_types.length == 0) {
                AnnotatedMethods.invoke(mAnnotatedMethod, mObject);
            } else if (parameter_types.length == 1 && View.class.isAssignableFrom(parameter_types[0])) {
                AnnotatedMethods.invoke(mAnnotatedMethod, mObject, v);
            } else {
                throw new BindException(BindClick.class, v.getClass(), mAnnotatedMethod.getMethod(), "onClick failed because the method arguments must be either empty or accept a single View type");
            }
        }
    }

    @Override
    public Class<BindClick> getAnnotationClass() {
        return BindClick.class;
    }

    @Override
    public void bind(final Object object, AnnotatedMethod<BindClick> annotatedMethod, @Nullable Object[] modules) {
        final View view = Views.getView(annotatedMethod.getAnnotation().value(), annotatedMethod.getMethod().getName(), object);

        view.setOnClickListener(new OnClickListener(annotatedMethod, object));
    }
}
