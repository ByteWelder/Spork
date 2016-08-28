package io.github.sporklibrary.android.bindclick.domain;

import android.content.Context;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.test.R;

public class TestFaultyView extends FrameLayout {

    public TestFaultyView(Context context) {
        super(context);
        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    public void onClick() {
    }
}