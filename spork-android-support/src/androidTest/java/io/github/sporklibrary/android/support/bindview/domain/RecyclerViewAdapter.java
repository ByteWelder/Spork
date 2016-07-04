package io.github.sporklibrary.android.support.bindview.domain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.annotations.BindView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TestViewHolder> {

    private final List<String> items;

    public RecyclerViewAdapter(List<String> items) {
        this.items = items;
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview)
        private TextView mTextView;

        public TestViewHolder(View itemView) {
            super(itemView);

            Spork.bind(this);
        }

        public void update(String text) {
            mTextView.setText(text);
        }
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_item, parent, false);

        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        String item = items.get(position);

        holder.update(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
