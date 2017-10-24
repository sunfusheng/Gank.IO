package com.sunfusheng.gank.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.MainApplication;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.ui.ImagesActivity;
import com.sunfusheng.gank.util.Util;
import com.sunfusheng.gank.util.DateUtil;
import com.sunfusheng.glideimageview.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author by sunfusheng on 2017/1/17.
 */
public class GirlItemViewBinder extends ItemViewBinder<GankItemGirl, GirlItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank_girl, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankItemGirl item) {
        holder.rlGirl.setTag(true);
        holder.tvTime.setTypeface(MainApplication.songTi);
        holder.tvTime.setText(DateUtil.convertString2String(item.publishedAt));
        holder.givGirl.loadImage(item.url, R.mipmap.she);

        Util.singleClick(holder.givGirl, o -> {
            ImagesActivity.startActivity(holder.givGirl.getContext(), MainApplication.girls, item.url);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_girl)
        RelativeLayout rlGirl;
        @BindView(R.id.giv_girl)
        GlideImageView givGirl;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
