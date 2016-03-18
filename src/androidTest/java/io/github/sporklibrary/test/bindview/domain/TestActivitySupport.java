package io.github.sporklibrary.test.bindview.domain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindview.ViewProvider;
import java.util.ArrayList;

public class TestActivitySupport extends AppCompatActivity implements ViewProvider
{
	@BindView(R.id.viewbindingview)
	private View mViewBindingView;

	@BindView
	private TestView viewbindingview;

	@BindView(R.id.test_recyclerview)
	private RecyclerView mRecyclerView;

	@BindFragment(R.id.viewbindingfragment)
	private TestFragmentSupport mViewBindingFragment;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_binding_support);

		Spork.bind(this);

		ArrayList<String> items = new ArrayList<>();
		items.add("Alpha");
		items.add("Beta");
		items.add("Gamma");
		items.add("Delta");

		RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setAdapter(adapter);
	}

	@Override
	public View getViewByIdSpecified()
	{
		return mViewBindingView;
	}

	@Override
	public View getViewByImplied()
	{
		return viewbindingview;
	}

	public TestView getViewBindingView()
	{
		return viewbindingview;
	}

	public TestFragmentSupport getViewBindingFragment()
	{
		return mViewBindingFragment;
	}

	public RecyclerView getRecyclerView()
	{
		return mRecyclerView;
	}
}