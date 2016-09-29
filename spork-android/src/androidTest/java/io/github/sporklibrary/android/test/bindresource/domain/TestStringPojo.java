package io.github.sporklibrary.android.test.bindresource.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.android.test.R;

public class TestStringPojo {

    @BindResource(R.string.app_name)
    private String test;

    public TestStringPojo() {
        Spork.bind(this);
    }
}
