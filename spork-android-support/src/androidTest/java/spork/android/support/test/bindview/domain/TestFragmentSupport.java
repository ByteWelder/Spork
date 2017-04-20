package spork.android.support.test.bindview.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import spork.SporkInstance;
import spork.android.BindView;
import spork.android.support.test.R;
import spork.android.support.test.bindview.ViewProvider;

public class TestFragmentSupport extends Fragment implements ViewProvider {

    // Keep this one public to have a different accessibily state on the Field
    @BindView(R.id.viewbindingfragment_button)
    public Button button;

    @BindView
    private Button viewbindingfragment_button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_binding, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SporkInstance.bind(this);
    }

    @Override
    public View getViewByIdSpecified() {
        return button;
    }

    @Override
    public View getViewByImplied() {
        return viewbindingfragment_button;
    }
}
