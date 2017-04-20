package spork.android.test.bindview.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import spork.SporkInstance;
import spork.android.BindView;
import spork.android.test.R;

public class FaultyTargetTypeView extends FrameLayout {

    @BindView(R.id.testview_button)
    private String button;

    public FaultyTargetTypeView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_test, this);

        SporkInstance.bind(this);
    }
}
