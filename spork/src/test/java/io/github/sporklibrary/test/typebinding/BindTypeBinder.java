package io.github.sporklibrary.test.typebinding;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedType;

public class BindTypeBinder implements TypeBinder<BindValue> {

    @Override
    public Class<BindValue> getAnnotationClass() {
        return BindValue.class;
    }

    @Override
    public void bind(Object object, AnnotatedType<BindValue> annotatedType, @Nullable Object[] modules) {
        if (!IntSettable.class.isAssignableFrom(object.getClass())) {
            throw new BindException(BindValue.class, object.getClass(), "can only be used with IntSettable target");
        }

        IntSettable valueHolder = (IntSettable) object;

        int value = annotatedType.getAnnotation().value();

        // Internal test for improved code coverage
        if (annotatedType.getAnnotatedClass() == null) {
            throw new RuntimeException("annotated class in AnnotatedType is null");
        }

        valueHolder.setValue(value);
    }
}
