package spork.android.test.bindresource.domain;

import spork.Spork;
import spork.android.BindResource;
import spork.android.test.R;

public class TestDimensionPojo {

    @BindResource(R.dimen.spork_test_dimension)
    private float test;

    public TestDimensionPojo() {
        Spork.bind(this);
    }
}
