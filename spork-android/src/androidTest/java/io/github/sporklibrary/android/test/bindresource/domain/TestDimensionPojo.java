package io.github.sporklibrary.android.test.bindresource.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.android.test.R;

public class TestDimensionPojo {

    @BindResource(R.dimen.spork_test_dimension)
    private float test;

    public TestDimensionPojo() {
        Spork.bind(this);
    }
}
