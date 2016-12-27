package com.sunfusheng.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.RefreshableWidget.recyclerview.BaseRecyclerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2016/12/27.
 */

public class TestAdapter extends BaseRecyclerAdapter<TestAdapter.ViewHolder> {

    private List<String> mList;
    private LayoutInflater mInflater;

    public TestAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.layout_test_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        holder.tvDesc.setText(mList.get(position));
    }

    @Override
    public int getAdapterItemCount() {
        return mList != null? mList.size():0;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view, false);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvDesc)
        TextView tvDesc;

        ViewHolder(View view, boolean isItem) {
            super(view);
            if (isItem) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
