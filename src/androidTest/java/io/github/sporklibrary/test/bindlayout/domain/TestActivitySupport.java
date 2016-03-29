package io.github.sporklibrary.test.bindlayout.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindLayout;
import io.github.sporklibrary.test.R;

@BindLayout(R.layout.activity_layout_binding)
public class TestActivitySupport extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);
    }
}