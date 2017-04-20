package spork.android.test.bindresource.domain;

import spork.SporkInstance;
import spork.android.BindResource;
import spork.android.test.R;

public class TestDimensionPojo {

    @BindResource(R.dimen.spork_test_dimension)
    private float test;

    public TestDimensionPojo() {
        SporkInstance.bind(this);
    }
}
