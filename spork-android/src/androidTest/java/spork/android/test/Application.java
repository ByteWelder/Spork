package spork.android.test;

import spork.SporkInstance;
import spork.android.BindResource;

public class Application extends android.app.Application {
    private static Application sInstance = null;

    @BindResource
    private Float spork_test_dimension;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        SporkInstance.bind(this);
    }

    public Float getTestDimension() {
        return spork_test_dimension;
    }

    public static Application getInstance() {
        return sInstance;
    }
}
