package spork.android.test.bindresource.domain;

import android.graphics.drawable.Drawable;

import spork.Spork;
import spork.android.annotations.BindResource;
import spork.android.test.R;

public class TestDrawablePojo {

    @BindResource(R.drawable.spork_test_drawable)
    private Drawable test;

    public TestDrawablePojo() {
        Spork.bind(this);
    }
}
