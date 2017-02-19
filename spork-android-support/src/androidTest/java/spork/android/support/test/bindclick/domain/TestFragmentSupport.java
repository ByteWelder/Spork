package spork.android.support.test.bindclick.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spork.Spork;
import spork.android.support.test.R;
import spork.android.BindClick;
import spork.android.support.test.bindclick.ClickTestProvider;

public class TestFragmentSupport extends Fragment implements ClickTestProvider {
    private int clickCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_click_binding, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
    }

    // Keep this one public to have a different accessibily state on the Method (as opposed to TestView)
    @BindClick(R.id.click_binding_fragment_button)
    public void onClick() {
        clickCount++;
    }

    @Override
    public int getClickCount() {
        return clickCount;
    }

    @Override
    public int getClickViewResourceId() {
        return R.id.click_binding_fragment_button;
    }
}
