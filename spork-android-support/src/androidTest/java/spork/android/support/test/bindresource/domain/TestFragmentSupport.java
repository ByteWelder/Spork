package spork.android.support.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spork.Spork;
import spork.android.annotations.BindResource;
import spork.android.support.test.R;
import spork.android.support.test.bindresource.ResourceProvider;

public class TestFragmentSupport extends Fragment implements ResourceProvider {

    @BindResource(R.string.app_name)
    private String appName;

    @BindResource
    private String app_name;

    @BindResource(R.dimen.spork_test_dimension)
    private float sporkTestDimension;

    @BindResource
    private Float spork_test_dimension;

    @BindResource(R.drawable.spork_test_drawable)
    private Drawable sporkTestDrawable;

    @BindResource
    private Drawable spork_test_drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return new View(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
    }

    @javax.annotation.Nullable
    @Override
    public String getStringByIdSpecified() {
        return appName;
    }

    @javax.annotation.Nullable
    @Override
    public String getStringByIdImplied() {
        return app_name;
    }

    @javax.annotation.Nullable
    @Override
    public Drawable getDrawableByIdSpecified() {
        return sporkTestDrawable;
    }

    @javax.annotation.Nullable
    @Override
    public Drawable getDrawableByIdImplied() {
        return spork_test_drawable;
    }

    @Override
    public float getDimensionByIdSpecified() {
        return sporkTestDimension;
    }

    @Override
    public float getDimensionByIdImplied() {
        return spork_test_dimension;
    }
}
