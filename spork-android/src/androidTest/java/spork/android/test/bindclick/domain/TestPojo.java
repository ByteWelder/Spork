package spork.android.test.bindclick.domain;

import spork.Spork;
import spork.android.annotations.BindClick;
import spork.android.test.R;

public class TestPojo {

    public TestPojo() {
        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    public void onClick() {
    }
}
