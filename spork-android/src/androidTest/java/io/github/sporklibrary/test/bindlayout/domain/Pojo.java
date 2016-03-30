package io.github.sporklibrary.test.bindlayout.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindLayout;
import io.github.sporklibrary.test.R;

@BindLayout(R.layout.view_layout_binding)
public class Pojo {

    public Pojo() {
        Spork.bind(this);
    }
}
