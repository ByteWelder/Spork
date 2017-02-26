package spork.android.test.bindresource.domain;

import spork.Spork;
import spork.android.BindResource;
import spork.android.test.R;

public class TestStringPojo {

    @BindResource(R.string.app_name)
    private String test;

    public TestStringPojo() {
        Spork.bind(this);
    }
}
