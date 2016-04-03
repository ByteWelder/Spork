package io.github.sporklibrary.test.components.bindparent;

import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentA;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentB;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentC;
import io.github.sporklibrary.test.components.bindparent.domain.FaultyComponents;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BindParentTests {

    @Test
    public void test() {
        ComponentA root = new ComponentA();
        ComponentB rootChild = root.getChild();

        assertNotNull(rootChild);
        assertNotNull(rootChild.getParent());

        ComponentC rootChildChild = rootChild.getChild();

        assertNotNull(rootChildChild);
        assertNotNull(rootChildChild.getParent());
    }

    @Test(expected = BindException.class)
    public void notAnnotated() {
        new FaultyComponents.NotAnnotatedParent();
    }

    @Test(expected = BindException.class)
    public void typeMismatch() {
        new FaultyComponents.TypeMismatchParent();
    }

    @Test(expected = BindException.class)
    public void singleton() {
        new FaultyComponents.SingletonParent();
    }

    @Test(expected = BindException.class)
    public void multiArgument() {
        new FaultyComponents.MultiArgumentParent();
    }
}
