package spork.android.test.bindlayout.domain;

import spork.Spork;
import spork.android.BindLayout;
import spork.android.test.R;

@BindLayout(R.layout.view_layout_binding)
public class Pojo {

    public Pojo() {
        Spork.bind(this);
    }
}
