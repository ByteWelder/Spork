package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.ComponentParent;

public class ComponentC {
    private ComponentB parent;

    public ComponentC(@ComponentParent ComponentB parent) {
        this.parent = parent;

        Spork.bind(this);
    }

    public ComponentB getParent() {
        return parent;
    }
}
