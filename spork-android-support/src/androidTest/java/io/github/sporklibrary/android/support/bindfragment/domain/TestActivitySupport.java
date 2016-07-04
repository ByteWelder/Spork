package io.github.sporklibrary.android.support.bindfragment.domain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.android.support.test.R;

public class TestActivitySupport extends AppCompatActivity {

    @BindFragment(R.id.testfragment)
    private TestFragmentSupport fragment;

    @BindFragment
    private TestFragmentSupport testfragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_binding_support);
        Spork.bind(this);
    }

    public Fragment getFragmentByIdSpecified() {
        return fragment;
    }

    public Fragment getFragmentByIdImplied() {
        return testfragment;
    }

    public TestFragmentSupport getTestFragment() {
        return fragment;
    }
}
