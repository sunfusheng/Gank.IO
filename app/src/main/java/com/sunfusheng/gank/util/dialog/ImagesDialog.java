package com.sunfusheng.gank.util.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sunfusheng.gank.GankApp;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.util.DensityUtil;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.sunfusheng.glideimageview.GlideImageView;

import java.util.List;

/**
 * Created by sunfusheng on 2017/5/17.
 */
public class ImagesDialog {

    private Context mContext;
    private List<String> images;
    private DialogView mDialogView;

    public ImagesDialog(Context context, List<String> images) {
        this.mContext = context;
        this.images = images;
        initDialog();
    }

    private void initDialog() {
        View view = View.inflate(mContext, R.layout.dialog_images, null);
        LinearLayout llContainer = (LinearLayout) view.findViewById(R.id.ll_container);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTypeface(GankApp.songTi);
        tvTitle.setText("查看效果图（共" + images.size() + "张）");
        addImageViews(images, llContainer);
        mDialogView = new DialogView(mContext, view);
        mDialogView.setGravity(Gravity.BOTTOM);
    }

    private void addImageViews(List<String> images, LinearLayout llContainer) {
        if (AppUtil.isEmpty(images)) return;
        for (int i = 0; i < images.size(); i++) {
            int gap = DensityUtil.dip2px(mContext, 10);
            GlideImageView iv = new GlideImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
            params.topMargin = gap;
            params.bottomMargin = gap;
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_START);
            iv.loadImage(images.get(i), R.color.transparent);
            llContainer.addView(iv);
        }
    }

    private GlideImageView createGlideImageView(String imageUrl) {
        GlideImageView glideImageView = new GlideImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
        glideImageView.setLayoutParams(params);

        RequestOptions requestOptions = glideImageView.requestOptions(R.color.transparent)
                .centerInside();

        GlideImageLoader imageLoader = glideImageView.getImageLoader();

        imageLoader.setOnGlideImageViewListener(imageUrl, (percent, isDone, exception) -> {

        });

        imageLoader.requestBuilder(imageUrl, requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(glideImageView);

        return glideImageView;
    }

    public void show() {
        if (mDialogView == null) {
            initDialog();
        }
        mDialogView.show();
    }
}
