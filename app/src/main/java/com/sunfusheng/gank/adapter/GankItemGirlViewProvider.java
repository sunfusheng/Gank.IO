package com.sunfusheng.gank.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.util.DateUtil;
import com.sunfusheng.gank.widget.MultiType.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankItemGirlViewProvider extends ItemViewProvider<GankItemGirl, GankItemGirlViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank_girl, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankItemGirl item) {
        holder.rlGirl.setTag(true);
        holder.tvTime.setText(DateUtil.convertString2String(item.publishedAt));
        Glide.with(holder.ivGirl.getContext())
                .load(item.url)
                .centerCrop()
                .placeholder(R.color.transparent)
                .crossFade()
                .into(holder.ivGirl);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_girl)
        RelativeLayout rlGirl;
        @BindView(R.id.iv_girl)
        ImageView ivGirl;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
