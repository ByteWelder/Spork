package spork.android.support.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.annotation.Nullable;

import spork.Spork;
import spork.android.support.test.bindresource.ResourceProvider;
import spork.android.support.test.R;
import spork.android.annotations.BindFragment;
import spork.android.annotations.BindLayout;
import spork.android.annotations.BindResource;

@BindLayout(R.layout.activity_resource_binding_support)
public class TestActivitySupport extends AppCompatActivity implements ResourceProvider {

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

    @BindFragment(R.id.resourcebindingfragment)
    private TestFragmentSupport resourceBindingFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public float getDimensionByIdSpecified() {
        return sporkTestDimension;
    }

    @Override
    public float getDimensionByIdImplied() {
        return spork_test_dimension;
    }

    public TestFragmentSupport getResourceBindingFragment() {
        return resourceBindingFragment;
    }
}
