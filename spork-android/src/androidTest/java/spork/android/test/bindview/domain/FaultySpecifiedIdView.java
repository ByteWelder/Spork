package spork.android.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import spork.SporkInstance;
import spork.android.BindView;
import spork.android.test.R;

public class FaultySpecifiedIdView extends FrameLayout {

    @BindView(R.id.testview)
    protected View faultyView;

    public FaultySpecifiedIdView(Context context) {
        super(context);

        SporkInstance.bind(this);
    }
}
