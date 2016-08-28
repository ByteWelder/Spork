package io.github.sporklibrary.android.bindview.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.test.R;

public class FaultyTargetTypeView extends FrameLayout {

    @BindView(R.id.testview_button)
    private String button;

    public FaultyTargetTypeView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_test, this);

        Spork.bind(this);
    }
}
