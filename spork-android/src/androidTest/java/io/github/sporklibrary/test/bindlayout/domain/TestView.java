package io.github.sporklibrary.test.bindlayout.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindLayout;
import io.github.sporklibrary.test.R;

@BindLayout(R.layout.view_layout_binding)
public class TestView extends FrameLayout {

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Spork.bind(this);
    }
}
