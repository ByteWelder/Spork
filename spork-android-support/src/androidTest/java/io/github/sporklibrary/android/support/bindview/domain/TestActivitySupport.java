package io.github.sporklibrary.android.support.bindview.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.android.support.bindview.ViewProvider;

import java.util.ArrayList;

public class TestActivitySupport extends AppCompatActivity implements ViewProvider {

    @BindView(R.id.viewbindingview)
    private View viewBindingView;

    @BindView
    private TestView viewbindingview;

    @BindView(R.id.test_recyclerview)
    private RecyclerView recyclerView;

    @BindFragment(R.id.viewbindingfragment)
    private TestFragmentSupport viewBindingFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_binding_support);

        Spork.bind(this);

        ArrayList<String> items = new ArrayList<>();
        items.add("Alpha");
        items.add("Beta");
        items.add("Gamma");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View getViewByIdSpecified() {
        return viewBindingView;
    }

    @Override
    public View getViewByImplied() {
        return viewbindingview;
    }

    public TestView getViewBindingView() {
        return viewbindingview;
    }

    public TestFragmentSupport getViewBindingFragment() {
        return viewBindingFragment;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}