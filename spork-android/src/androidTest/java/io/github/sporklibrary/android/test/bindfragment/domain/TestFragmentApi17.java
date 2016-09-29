package io.github.sporklibrary.android.test.bindfragment.domain;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.test.R;

/**
 * Child fragment testing.
 */
public class TestFragmentApi17 extends Fragment {

    @BindFragment(R.id.testfragment)
    private Fragment fragment;

    @BindFragment
    private Fragment testfragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_binding_api17, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
    }

    public Fragment getFragmentByIdSpecified() {
        return fragment;
    }

    public Fragment getFragmentByIdImplied() {
        return testfragment;
    }
}
