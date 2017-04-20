package spork.android.test.bindlayout.domain;

import android.content.Context;
import android.widget.FrameLayout;

import spork.SporkInstance;
import spork.android.BindLayout;

@BindLayout(Integer.MIN_VALUE)
public class FaultyLayoutTestView extends FrameLayout {

    public FaultyLayoutTestView(Context context) {
        super(context);
        SporkInstance.bind(this);
    }
}
