package com.sunfusheng.gank.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.ui.WebActivity;
import com.sunfusheng.gank.util.MoreActionHelper;
import com.sunfusheng.gank.util.SpannableUtil;
import com.sunfusheng.gank.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author by sunfusheng on 2017/1/17.
 */
public class ContentItemViewBinder extends ItemViewBinder<GankItem, ContentItemViewBinder.ViewHolder> {

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
            String wholeText = item.desc + " - " + item.who;
            String targetText = " - " + item.who;
            int targetTextColor = holder.tvDesc.getContext().getResources().getColor(R.color.md_grey_400);
            holder.tvDesc.setText(SpannableUtil.getSpannableString(wholeText, targetText, targetTextColor));
        }

        ViewUtil.singleClick(holder.rlGank, o -> {
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
