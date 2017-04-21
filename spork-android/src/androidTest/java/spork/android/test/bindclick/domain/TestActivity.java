package spork.android.test.bindclick.domain;

import android.app.Activity;
import android.os.Bundle;

import spork.Spork;
import spork.android.BindClick;
import spork.android.BindFragment;
import spork.android.BindView;
import spork.android.test.R;
import spork.android.test.bindclick.ClickTestProvider;

public class TestActivity extends Activity implements ClickTestProvider {

    @BindFragment(R.id.testfragment)
    private TestFragment testFragment;

    @BindView(R.id.testview)
    private TestView testView;

    @BindView(R.id.testfaultyclickargumentsview)
    private TestFaultyClickArgumentsView testFaultyClickArgumentsView;

    private int clickCount = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_binding);
        Spork.bind(this);
    }

    @BindClick(R.id.click_binding_activity_button)
    private void onClick() {
        clickCount++;
    }

    @Override
    public int getClickCount() {
        return clickCount;
    }

    @Override
    public int getClickViewResourceId() {
        return R.id.click_binding_activity_button;
    }

    public TestFragment getTestFragment() {
        return testFragment;
    }

    public TestView getTestView() {
        return testView;
    }

    public TestFaultyClickArgumentsView getTestFaultyClickArgumentsView() {
        return testFaultyClickArgumentsView;
    }
}
