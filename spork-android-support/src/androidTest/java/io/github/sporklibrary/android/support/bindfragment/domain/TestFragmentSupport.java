package io.github.sporklibrary.android.support.bindfragment.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.support.test.R;

public class TestFragmentSupport extends Fragment {

    @BindFragment(R.id.testfragment)
    private Fragment fragment;

    @BindFragment
    private Fragment testfragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_binding_support, container);
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
