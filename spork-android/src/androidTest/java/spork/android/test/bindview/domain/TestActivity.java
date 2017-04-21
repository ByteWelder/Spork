package spork.android.test.bindview.domain;

import android.os.Bundle;
import android.view.View;

import spork.Spork;
import spork.android.BindFragment;
import spork.android.BindLayout;
import spork.android.BindView;
import spork.android.test.R;
import spork.android.test.bindview.ViewProvider;

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
