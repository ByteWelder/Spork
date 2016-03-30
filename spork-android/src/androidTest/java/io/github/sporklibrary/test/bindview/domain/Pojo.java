package io.github.sporklibrary.test.bindview.domain;

import android.widget.Button;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

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
