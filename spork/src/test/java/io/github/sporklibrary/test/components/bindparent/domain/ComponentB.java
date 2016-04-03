package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentParent;

public class ComponentB {

    @BindComponent
    private ComponentC child;

    private ComponentA parent;

    public ComponentB(@ComponentParent ComponentA parent) {
        this.parent = parent;

        Spork.bind(this);
    }

    public ComponentC getChild() {
        return child;
    }

    public ComponentA getParent() {
        return parent;
    }
}
