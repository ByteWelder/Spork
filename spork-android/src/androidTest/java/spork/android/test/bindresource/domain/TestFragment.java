package spork.android.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spork.Spork;
import spork.android.BindResource;
import spork.android.test.R;
import spork.android.test.bindresource.ResourceProvider;

public class TestFragment extends android.app.Fragment implements ResourceProvider {

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

    @BindResource
    private int spork_test_int;

    @BindResource(R.integer.spork_test_int)
    private Integer spork_test_integer;

    @BindResource
    private boolean spork_test_boolean;

    @BindResource(R.bool.spork_test_boolean)
    private Boolean spork_test_boolean_object;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return new View(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
    }

    @Nullable
    @Override
    public String getStringByIdSpecified() {
        return appName;
    }

    @Nullable
    @Override
    public String getStringByIdImplied() {
        return app_name;
    }

    @Nullable
    @Override
    public Drawable getDrawableByIdSpecified() {
        return sporkTestDrawable;
    }

    @Nullable
    @Override
    public Drawable getDrawableByIdImplied() {
        return spork_test_drawable;
    }

    @Override
    public int getIntByIdImplied() {
        return spork_test_int;
    }

    @Override
    public Integer getIntegerByIdSpecified() {
        return spork_test_integer;
    }

    @Override
    public float getDimensionByIdSpecified() {
        return sporkTestDimension;
    }

    @Override
    public float getDimensionByIdImplied() {
        return spork_test_dimension;
    }

    @Override
    public boolean getBooleanByIdImplied() {
        return spork_test_boolean;
    }

    @javax.annotation.Nullable
    @Override
    public Boolean getBooleanByIdSpecified() {
        return spork_test_boolean_object;
    }
}
