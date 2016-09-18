package io.github.sporklibrary.test.components;

import org.junit.Test;

import java.lang.reflect.Field;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.components.scope.DefaultScopeComponent;
import io.github.sporklibrary.test.components.scope.FaultyConstructorComponent;
import io.github.sporklibrary.test.components.scope.SingletonScopeComponent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentScopeTests {

    public static class Parent {

        // must be public for easy test access
        @BindComponent
        public DefaultScopeComponent defaultScopeComponent;

        @BindComponent
        private SingletonScopeComponent singletonScopeComponent;

        public Parent() {
            Spork.bind(this);
        }

        public SingletonScopeComponent getSingletonScopedComponent() {
            return singletonScopeComponent;
        }

        public DefaultScopeComponent getDefaultScopeComponent() {
            return defaultScopeComponent;
        }
    }

    public static class FaultyConstructorParent {

        @BindComponent
        private FaultyConstructorComponent faultyConstructorComponent;

        public FaultyConstructorParent() {
            Spork.bind(this);
        }

        public FaultyConstructorComponent getFaultyComponent() {
            return faultyConstructorComponent;
        }
    }

    @Test
    public void defaultImpliedScopeClass() throws NoSuchFieldException {
        Parent parent = new Parent();
        Field defaultScopedComponentField = parent.getClass().getField("defaultScopeComponent");
        BindComponent bindComponentAnnotation = defaultScopedComponentField.getAnnotation(BindComponent.class);
        assertNotNull(bindComponentAnnotation);
        assertTrue(BindComponent.Default.class.equals(bindComponentAnnotation.value()));
    }

    @Test
    public void binding() {
        Parent firstParent = new Parent();
        Parent secondParent = new Parent();
        assertNotNull("default implied scoped component binding", firstParent.getDefaultScopeComponent());
        assertNotNull("singleton scoped component binding", firstParent.getSingletonScopedComponent());
        assertTrue("default implied scope instance uniqueness", firstParent.getDefaultScopeComponent() != secondParent.getDefaultScopeComponent());
        assertTrue("instance scope instance uniqueness", firstParent.getSingletonScopedComponent() == secondParent.getSingletonScopedComponent());
    }

    @Test(expected = BindException.class)
    public void bindingFailure() {
        new FaultyConstructorParent();
    }
}
