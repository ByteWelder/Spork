package io.github.sporklibrary.android;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.android.inject.domain.StringModule;
import io.github.sporklibrary.annotations.Inject;

public class Application extends android.app.Application {
    private static Application sInstance = null;

    @BindResource
    private Float spork_test_dimension;

    @Inject
    private String testString;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Spork.bind(this, new StringModule());
    }

    public Float getTestDimension() {
        return spork_test_dimension;
    }

    public String getTestString() {
        return testString;
    }

    public static Application getInstance() {
        return sInstance;
    }
}
