package spork.android.test.bindresource.domain;

import android.graphics.drawable.Drawable;

import spork.SporkInstance;
import spork.android.BindResource;
import spork.android.test.R;

public class TestDrawablePojo {

    @BindResource(R.drawable.spork_test_drawable)
    private Drawable test;

    public TestDrawablePojo() {
        SporkInstance.bind(this);
    }
}
