package spork.android.test.bindlayout.domain;

import android.app.Activity;
import android.os.Bundle;

import spork.Spork;
import spork.android.annotations.BindLayout;
import spork.android.test.R;

@BindLayout(R.layout.activity_layout_binding)
public class TestActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);
    }
}
