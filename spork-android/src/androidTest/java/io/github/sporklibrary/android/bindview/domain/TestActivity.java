package io.github.sporklibrary.android.bindview.domain;

import android.os.Bundle;
import android.view.View;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.test.R;
import io.github.sporklibrary.android.bindview.ViewProvider;

@BindLayout(R.layout.activity_view_binding)
public class TestActivity extends android.app.Activity implements ViewProvider {

    @BindView(R.id.viewbindingview)
    private View viewBindingView;

    @BindView
    private TestView viewbindingview;

    @BindFragment(R.id.viewbindingfragment)
    private TestFragment mViewBindingFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);
    }

    @Override
    public View getViewByIdSpecified() {
        return viewBindingView;
    }

    @Override
    public View getViewByImplied() {
        return viewbindingview;
    }

    public TestView getViewBindingView() {
        return viewbindingview;
    }

    public TestFragment getViewBindingFragment() {
        return mViewBindingFragment;
    }
}
