package io.github.sporklibrary.binders.component.factories;

import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentParent;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.component.ComponentFactory;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotSupportedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The standard component factory: creates scoped component instances.
 */
public class DefaultComponentFactory implements ComponentFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object create(Class<?> classObject, @Nullable Object parent) {
        try {
            if (classObject.getConstructors().length != 1) {
                throw new BindException(BindComponent.class, classObject, "components must have exactly 1 public constructor (explicit or implied)");
            }

            Constructor<?> constructor = classObject.getConstructors()[0];

            boolean accessible = constructor.isAccessible();

            // ensure constructor can be invoked
            if (!accessible) {
                constructor.setAccessible(true);
            }

            Object[] constructorArguments = getConstructorArguments(constructor, parent);

            Object instance = constructor.newInstance(constructorArguments);

            // reset accessibility
            if (!accessible) {
                constructor.setAccessible(false);
            }

            return instance;
        } catch (InvocationTargetException e) {
            throw new BindException(BindComponent.class, classObject, "constructor threw exception", e);
        } catch (Exception e) {
            throw new BindException(BindComponent.class, classObject, "failed to create instance", e);
        }
    }

    // Warning: This needs to be JDK 1.5 compatible because of Android support
    private Object[] getConstructorArguments(Constructor<?> constructor, @Nullable Object parent) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        if (parameterTypes.length == 0) {
            return new Object[0];
        } else if (parameterTypes.length == 1) {
            Class<?> parameterType = parameterTypes[0];

            Annotation[] annotations = constructor.getParameterAnnotations()[0];

            ComponentParent annotation = null;

            for (Annotation a : annotations) {
                if (ComponentParent.class.isAssignableFrom(a.getClass())) {
                    annotation = (ComponentParent) a;
                    break;
                }
            }

            if (annotation == null) {
                throw new BindException(BindComponent.class, "component constructor has an invalid parameter or the constructor parameter is missing the @ComponentParent annotation");
            }

            if (parent == null) {
                throw new BindException(BindComponent.class, "@ComponentParent only works with default-scoped components");
            }

            if (!parameterType.isAssignableFrom(parent.getClass())) {
                throw new BindException(BindComponent.class, "@ComponentParent target type is not compatible with the actual parent type");
            }

            return new Object[]{parent};
        } else {
            throw new NotSupportedException("component constructor must have 0 or 1 attributes");
        }
    }
}
