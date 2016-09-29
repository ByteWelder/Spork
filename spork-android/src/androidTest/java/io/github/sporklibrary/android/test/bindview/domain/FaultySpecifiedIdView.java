package io.github.sporklibrary.android.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.test.R;

public class FaultySpecifiedIdView extends FrameLayout {

    @BindView(R.id.testview)
    protected View faultyView;

    public FaultySpecifiedIdView(Context context) {
        super(context);

        Spork.bind(this);
    }
}
