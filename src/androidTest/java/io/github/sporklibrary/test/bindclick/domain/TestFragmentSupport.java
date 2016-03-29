package io.github.sporklibrary.test.bindclick.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.test.bindclick.ClickTestProvider;

public class TestFragmentSupport extends Fragment implements ClickTestProvider {
    private int clickCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(io.github.sporklibrary.test.R.layout.fragment_click_binding, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
    }

    // Keep this one public to have a different accessibily state on the Method (as opposed to TestView)
    @BindClick(io.github.sporklibrary.test.R.id.click_binding_fragment_button)
    public void onClick() {
        clickCount++;
    }

    @Override
    public int getClickCount() {
        return clickCount;
    }

    @Override
    public int getClickViewResourceId() {
        return io.github.sporklibrary.test.R.id.click_binding_fragment_button;
    }
}
