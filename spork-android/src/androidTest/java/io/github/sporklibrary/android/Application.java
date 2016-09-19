package io.github.sporklibrary.android;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.annotations.Inject;

public class Application extends android.app.Application {
    private static Application sInstance = null;

    public static class Component {
    }

    @BindResource
    private Float spork_test_dimension;

    @Inject
    private Component component;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Spork.bind(this);
    }

    public Float getTestDimension() {
        return spork_test_dimension;
    }

    public Component getComponent() {
        return component;
    }

    public static Application getInstance() {
        return sInstance;
    }
}
