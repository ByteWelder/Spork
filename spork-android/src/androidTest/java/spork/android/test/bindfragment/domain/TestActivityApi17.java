package spork.android.test.bindfragment.domain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import spork.Spork;
import spork.android.annotations.BindFragment;
import spork.android.annotations.BindLayout;
import spork.android.test.R;

/**
 * Tests child fragments.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@BindLayout(R.layout.activity_fragment_binding_api17)
public class TestActivityApi17 extends Activity {

    @BindFragment(R.id.testfragmentapi17)
    private TestFragmentApi17 testFragment;

    @BindFragment
    private TestFragmentApi17 testfragmentapi17;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
