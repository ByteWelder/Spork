package spork.android.test.bindlayout.domain;

import spork.SporkInstance;
import spork.android.BindLayout;
import spork.android.test.R;

@BindLayout(R.layout.view_layout_binding)
public class Pojo {

    public Pojo() {
        SporkInstance.bind(this);
    }
}
