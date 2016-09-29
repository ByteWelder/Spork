package io.github.sporklibrary.android.test.bindresource.domain;

import android.graphics.drawable.Drawable;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.android.test.R;

public class TestDrawablePojo {

    @BindResource(R.drawable.spork_test_drawable)
    private Drawable test;

    public TestDrawablePojo() {
        Spork.bind(this);
    }
}
