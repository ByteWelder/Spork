package spork.android.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import spork.SporkInstance;
import spork.android.BindView;

public class FaultyImpliedIdView extends FrameLayout {

    @BindView
    protected View faultyView;

    public FaultyImpliedIdView(Context context) {
        super(context);

        SporkInstance.bind(this);
    }
}
