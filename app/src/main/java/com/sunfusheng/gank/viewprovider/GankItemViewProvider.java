package com.sunfusheng.gank.viewprovider;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.ui.WebActivity;
import com.sunfusheng.gank.util.Util;
import com.sunfusheng.gank.util.MoreActionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankItemViewProvider extends ItemViewBinder<GankItem, GankItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank, parent, false);
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

        Util.singleClick(holder.rlGank, o -> {
            WebActivity.startActivity(holder.tvDesc.getContext(), item);
        });

        holder.rlGank.setOnLongClickListener(v -> true);

        holder.ivMore.setOnClickListener(v -> MoreActionHelper.showMoreMenu(holder.ivMore, item));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_gank)
        RelativeLayout rlGank;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
