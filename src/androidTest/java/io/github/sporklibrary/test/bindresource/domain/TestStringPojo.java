package io.github.sporklibrary.test.bindresource.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindResource;

public class TestStringPojo {

    @BindResource(io.github.sporklibrary.test.R.string.app_name)
    private String test;

    public TestStringPojo() {
        Spork.bind(this);
    }
}
