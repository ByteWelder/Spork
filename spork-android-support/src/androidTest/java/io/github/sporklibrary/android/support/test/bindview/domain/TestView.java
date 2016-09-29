package io.github.sporklibrary.android.support.test.bindview.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.support.test.bindview.ViewProvider;

public class TestView extends FrameLayout implements ViewProvider {

    @BindView(R.id.viewbindingview_button)
    private Button button;

    @BindView
    private Button viewbindingview_button;

    public TestView(Context context) {
        super(context);
        init(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_view_binding, this);

        Spork.bind(this);
    }

    @Override
    public android.view.View getViewByIdSpecified() {
        return button;
    }

    @Override
    public android.view.View getViewByImplied() {
        return viewbindingview_button;
    }
}
