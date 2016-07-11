package io.github.sporklibrary.android.bindfragment.domain;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.test.R;

@BindLayout(R.layout.activity_fragment_binding)
public class TestActivity extends Activity {

    @BindFragment(R.id.testfragment)
    private TestFragment fragment;

    @BindFragment
    private TestFragment testfragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
    }

    public Fragment getFragmentByIdSpecified() {
        return fragment;
    }

    public Fragment getFragmentByIdImplied() {
        return testfragment;
    }
}
