package spork.android.test.bindlayout.domain;

import android.app.Activity;
import android.os.Bundle;

import spork.SporkInstance;
import spork.android.BindLayout;
import spork.android.test.R;

@BindLayout(R.layout.activity_layout_binding)
public class TestActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SporkInstance.bind(this);
    }
}
