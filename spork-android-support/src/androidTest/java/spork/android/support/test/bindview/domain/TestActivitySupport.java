package spork.android.support.test.bindview.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import spork.Spork;
import spork.android.BindFragment;
import spork.android.BindLayout;
import spork.android.BindView;
import spork.android.support.test.R;

import java.util.ArrayList;

@BindLayout(R.layout.activity_view_binding_support)
public class TestActivitySupport extends AppCompatActivity {

    @BindView(R.id.test_recyclerview)
    private RecyclerView recyclerView;

    @BindFragment(R.id.viewbindingfragment)
    private TestFragmentSupport viewBindingFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);

        ArrayList<String> items = new ArrayList<>();
        items.add("Alpha");
        items.add("Beta");
        items.add("Gamma");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public TestFragmentSupport getViewBindingFragment() {
        return viewBindingFragment;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}