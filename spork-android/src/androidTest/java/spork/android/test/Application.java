package spork.android.test;

import javax.inject.Inject;

import spork.Spork;
import spork.android.annotations.BindResource;
import spork.android.test.domain.StringModule;

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
