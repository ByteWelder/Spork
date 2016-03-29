package io.github.sporklibrary.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;

public class FaultyImpliedIdView extends FrameLayout {

    @BindView
    protected View faultyView;

    public FaultyImpliedIdView(Context context) {
        super(context);

        Spork.bind(this);
    }
}
