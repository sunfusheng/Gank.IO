package com.sunfusheng.gank.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.widget.MultiType.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankItemViewProvider extends ItemViewProvider<GankItem, GankItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank, parent, false);
        view.setOnClickListener(v -> {

        });
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankItem item) {
        if (TextUtils.isEmpty(item.who)) {
            holder.tvDesc.setText(item.desc);
        } else {
            int start = item.desc.length();
            int end = start + item.who.length() + 3;
            int color = holder.tvDesc.getContext().getResources().getColor(R.color.md_grey_400);
            SpannableStringBuilder ssb = new SpannableStringBuilder(item.desc + " - " + item.who);
            ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new RelativeSizeSpan(0.85f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvDesc.setText(ssb);
        }

        holder.tvDesc.setOnClickListener(v -> {

        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
