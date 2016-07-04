package io.github.sporklibrary.android.support.bindclick.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.android.support.bindclick.ClickTestProvider;

import java.util.ArrayList;

public class TestActivitySupport extends AppCompatActivity implements ClickTestProvider {

    @BindFragment(R.id.testfragment)
    private TestFragmentSupport testFragment;

    @BindView(R.id.testview)
    private View testView;

    @BindView(R.id.test_recyclerview)
    private RecyclerView recyclerView;

    private int clickCount = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_click_binding_support);

        Spork.bind(this);

        ArrayList<String> items = new ArrayList<>();
        items.add("Alpha");
        items.add("Beta");
        items.add("Gamma");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @BindClick(R.id.click_binding_activity_button)
    private void onClick() {
        clickCount++;
    }

    @Override
    public int getClickCount() {
        return clickCount;
    }

    @Override
    public int getClickViewResourceId() {
        return R.id.click_binding_activity_button;
    }

    public TestFragmentSupport getTestFragment() {
        return testFragment;
    }

    public View getTestView() {
        return testView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
