package spork.android.test.bindclick.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import spork.SporkInstance;
import spork.android.BindClick;
import spork.android.test.R;
import spork.android.test.bindclick.ClickTestProvider;

public class TestView extends FrameLayout implements ClickTestProvider {
    private int clickCount = 0;

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
        LayoutInflater.from(context).inflate(R.layout.view_click_binding, this);

        SporkInstance.bind(this);
    }

    @BindClick(R.id.click_binding_view_button)
    private void onClick(Button button) {
        clickCount++;
    }

    @Override
    public int getClickCount() {
        return clickCount;
    }

    @Override
    public int getClickViewResourceId() {
        return R.id.click_binding_view_button;
    }
}
