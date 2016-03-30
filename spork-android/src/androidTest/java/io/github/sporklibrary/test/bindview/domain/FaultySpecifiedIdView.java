package io.github.sporklibrary.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class FaultySpecifiedIdView extends FrameLayout {

    @BindView(R.id.testview)
    protected View faultyView;

    public FaultySpecifiedIdView(Context context) {
        super(context);

        Spork.bind(this);
    }
}
