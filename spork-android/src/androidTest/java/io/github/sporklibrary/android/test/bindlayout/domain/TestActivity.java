package io.github.sporklibrary.android.test.bindlayout.domain;

import android.app.Activity;
import android.os.Bundle;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.test.R;

@BindLayout(R.layout.activity_layout_binding)
public class TestActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);
    }
}
