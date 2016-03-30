package io.github.sporklibrary.test.bindclick.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.test.R;

public class TestFaultyClickArgumentsView extends FrameLayout {

    public TestFaultyClickArgumentsView(Context context) {
        super(context);
        init(context);
    }

    public TestFaultyClickArgumentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestFaultyClickArgumentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestFaultyClickArgumentsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_click_binding_faulty_arguments, this);

        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_view_faulty_arguments_button)
    private void onClick(Button button, int testFailureParameter) {
    }
}
