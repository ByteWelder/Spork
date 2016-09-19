package io.github.sporklibrary.android.binders;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.internal.reflection.AnnotatedField;
import io.github.sporklibrary.internal.reflection.AnnotatedFields;
import io.github.sporklibrary.android.resolvers.FragmentResolverManager;
import io.github.sporklibrary.android.utils.ResourceId;

public class BindFragmentBinder implements FieldBinder<BindFragment> {

    @Override
    public Class<BindFragment> getAnnotationClass() {
        return BindFragment.class;
    }

    @Override
    public void bind(Object object, AnnotatedField<BindFragment> annotatedField, @Nullable Object[] modules) {
        Object fragment_object = resolveFragment(object, annotatedField);

        if (fragment_object == null) {
            throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "Fragment not found");
        }

        if (!annotatedField.getField().getType().isAssignableFrom(fragment_object.getClass())) {
            throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "field is not a Fragment");
        }

        AnnotatedFields.setValue(annotatedField, object, fragment_object);
    }

    private @Nullable Object resolveFragment(Object object, AnnotatedField<BindFragment> annotatedField) {
        BindFragment annotation = annotatedField.getAnnotation();
        int id = annotation.value();

        if (id != ResourceId.sDefaultValue) {
            return FragmentResolverManager.shared().resolveFragment(object, id);
        } else {
            Field field = annotatedField.getField();
            return FragmentResolverManager.shared().resolveFragment(object, field.getName());
        }
    }
}