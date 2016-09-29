package io.github.sporklibrary.android.test.bindclick.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.test.R;

public class TestPojo {

    public TestPojo() {
        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    public void onClick() {
    }
}
