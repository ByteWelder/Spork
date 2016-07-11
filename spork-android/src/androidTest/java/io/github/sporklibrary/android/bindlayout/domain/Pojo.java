package io.github.sporklibrary.android.bindlayout.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.test.R;

@BindLayout(R.layout.view_layout_binding)
public class Pojo {

    public Pojo() {
        Spork.bind(this);
    }
}
