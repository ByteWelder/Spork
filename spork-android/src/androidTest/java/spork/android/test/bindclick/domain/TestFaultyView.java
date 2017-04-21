package spork.android.test.bindclick.domain;

import android.content.Context;
import android.widget.FrameLayout;

import spork.Spork;
import spork.android.BindClick;
import spork.android.test.R;

public class TestFaultyView extends FrameLayout {

    public TestFaultyView(Context context) {
        super(context);
        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    public void onClick() {
    }
}