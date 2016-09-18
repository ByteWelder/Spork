package io.github.sporklibrary.test.methodbinding;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.reflection.AnnotatedMethod;
import io.github.sporklibrary.binders.MethodBinder;

public class BindMethodBinder implements MethodBinder<BindMethod> {
    private int methodCount = 0;

    @Override
    public Class<BindMethod> getAnnotationClass() {
        return BindMethod.class;
    }

    @Override
    public void bind(Object object, AnnotatedMethod<BindMethod> annotatedField, @Nullable Object[] modules) {
        methodCount++;
    }

    public int getMethodCount() {
        return methodCount;
    }
}
