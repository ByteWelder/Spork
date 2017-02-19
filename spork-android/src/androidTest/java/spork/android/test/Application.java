package spork.android.test;

import spork.Spork;
import spork.android.BindResource;

public class Application extends android.app.Application {
    private static Application sInstance = null;

    @BindResource
    private Float spork_test_dimension;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Spork.bind(this);
    }

    public Float getTestDimension() {
        return spork_test_dimension;
    }

    public static Application getInstance() {
        return sInstance;
    }
}
