package spork.android.test.bindview.domain;

import android.widget.Button;

import spork.Spork;
import spork.android.BindView;
import spork.android.test.R;

/**
 * An Object that is not derived from View
 */
public class Pojo {

    @BindView(R.id.testview_button)
    private Button button;

    public Pojo() {
        Spork.bind(this);
    }
}
