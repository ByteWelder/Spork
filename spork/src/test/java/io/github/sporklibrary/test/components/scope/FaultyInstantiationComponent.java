package io.github.sporklibrary.test.components.scope;

public class FaultyInstantiationComponent {

    public FaultyInstantiationComponent() {
        throw new RuntimeException("faulty instantiation");
    }
}
