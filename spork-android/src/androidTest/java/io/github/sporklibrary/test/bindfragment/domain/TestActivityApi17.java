package io.github.sporklibrary.test.bindfragment.domain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.R;

/**
 * Tests child fragments.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class TestActivityApi17 extends Activity {

    @BindFragment(R.id.testfragmentapi17)
    private TestFragmentApi17 testFragment;

    @BindFragment
    private TestFragmentApi17 testfragmentapi17;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_binding_api17);
        Spork.bind(this);
    }

    public Fragment getFragmentByIdSpecified() {
        return testFragment;
    }

    public Fragment getFragmentByIdImplied() {
        return testfragmentapi17;
    }

    public TestFragmentApi17 getTestFragment() {
        return testfragmentapi17;
    }
}
