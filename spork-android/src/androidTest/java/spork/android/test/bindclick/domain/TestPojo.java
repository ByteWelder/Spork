package spork.android.test.bindclick.domain;

import spork.SporkInstance;
import spork.android.BindClick;
import spork.android.test.R;

public class TestPojo {

    public TestPojo() {
        SporkInstance.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    public void onClick() {
    }
}
