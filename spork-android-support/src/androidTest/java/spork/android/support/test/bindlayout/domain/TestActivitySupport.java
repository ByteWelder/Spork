package spork.android.support.test.bindlayout.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import spork.SporkInstance;
import spork.android.BindLayout;
import spork.android.support.test.R;

@BindLayout(R.layout.activity_layout_binding)
public class TestActivitySupport extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SporkInstance.bind(this);
    }
}