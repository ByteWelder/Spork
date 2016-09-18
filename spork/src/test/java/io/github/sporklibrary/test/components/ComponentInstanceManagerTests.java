package io.github.sporklibrary.test.components;

import org.junit.Test;

import java.lang.reflect.Field;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.binders.component.ComponentInstanceManager;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.test.components.scope.DefaultScopeComponent;
import io.github.sporklibrary.test.components.scope.FaultyConstructorComponent;
import io.github.sporklibrary.test.components.scope.FaultyInstantiationComponent;
import io.github.sporklibrary.test.components.scope.SingletonScopeComponent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentInstanceManagerTests {

    // Fields are public for easy test access
    public static class Parent {

        @BindComponent
        public DefaultScopeComponent defaultScopeComponent;

        @BindComponent
        public SingletonScopeComponent singletonScopeComponent;

        public Parent() {
            Spork.bind(this);
        }
    }

    public static class FaultyConstructorParent {

        @BindComponent
        private FaultyConstructorComponent child;

        public FaultyConstructorParent() {
            Spork.bind(this);
        }
    }

    public static class FaultyInstantiationParent {

        @BindComponent
        public FaultyInstantiationComponent child;

        public FaultyInstantiationParent() {
            Spork.bind(this);
        }
    }

    @Test
    public void componentInstanceManager() throws NoSuchFieldException {
        ComponentInstanceManager manager = new ComponentInstanceManager();

        Parent parent = new Parent();

        Field defaultScopeField = parent.getClass().getField("defaultScopeComponent");
        Field singletonScopeField = parent.getClass().getField("singletonScopeComponent");

        BindComponent defaultImpliedAnnotation = defaultScopeField.getAnnotation(BindComponent.class);
        BindComponent singletonAnnotation = singletonScopeField.getAnnotation(BindComponent.class);

        assertNotNull(defaultImpliedAnnotation);
        assertNotNull(singletonAnnotation);

        AnnotatedField<BindComponent> defaultImpliedAnnotatedField = new AnnotatedField<>(defaultImpliedAnnotation, defaultScopeField);
        AnnotatedField<BindComponent> singletonAnnotatedField = new AnnotatedField<>(singletonAnnotation, singletonScopeField);

        Object defaultImpliedInstance = manager.getInstance(parent, defaultImpliedAnnotatedField);
        Object singletonInstance = manager.getInstance(parent, singletonAnnotatedField);
        Object singletonInstanceCopy = manager.getInstance(parent, singletonAnnotatedField);

        assertNotNull(defaultImpliedInstance);
        assertNotNull(singletonInstance);
        assertNotNull(singletonInstanceCopy);

        assertTrue(DefaultScopeComponent.class.isAssignableFrom(defaultImpliedInstance.getClass()));
        assertTrue(SingletonScopeComponent.class.isAssignableFrom(singletonInstance.getClass()));
        assertTrue(SingletonScopeComponent.class.isAssignableFrom(singletonInstanceCopy.getClass()));

        assertTrue(singletonInstance == singletonInstanceCopy);
    }

    @Test(expected = BindException.class)
    public void faultyComponentConstructor() {
        new FaultyConstructorParent();
    }

    @Test(expected = BindException.class)
    public void faultyInstantiationConstructor() {
        new FaultyInstantiationParent();
    }
}
