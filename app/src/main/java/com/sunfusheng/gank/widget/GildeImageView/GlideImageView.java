package com.sunfusheng.gank.widget.GildeImageView;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

/**
 * Created by sunfusheng on 2017/1/22.
 */
public class GlideImageView extends ImageView {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String SEPARATOR = "/";

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    // 将资源ID转为Uri
    public Uri resIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + SEPARATOR + resourceId);
    }

    // 加载网络图片
    public void loadNetImage(String url, int holderResId) {
        urlBuilder(url, holderResId).into(this);
    }

    // 加载drawable图片
    public void loadResImage(@IdRes int resId, int holderResId) {
        resBuilder(resId, holderResId).into(this);
    }

    // 加载本地图片
    public void loadLocalPathImage(String path, int holderResId) {
        urlBuilder("file://" + path, holderResId).into(this);
    }

    // 加载网络圆型图片
    public void loadNetCircleImage(String url, int holderResId) {
        urlBuilder(url, holderResId)
                .transform(new GlideCircleTransform(getContext()))
                .into(this);
    }

    // 加载drawable圆型图片
    public void loadLocalResCircleImage(int resId, int holderResId) {
        resBuilder(resId, holderResId)
                .transform(new GlideCircleTransform(getContext()))
                .into(this);
    }

    // 加载本地圆型图片
    public void loadLocalPathCircleImage(String path, int holderResId) {
        urlBuilder("file://" + path, holderResId)
                .transform(new GlideCircleTransform(getContext()))
                .into(this);
    }

    // 创建 Res DrawableRequestBuilder
    public DrawableRequestBuilder resBuilder(int resId, int holderResId) {
        return uriBuilder(resIdToUri(resId), holderResId);
    }

    // 创建 Uri DrawableRequestBuilder
    public DrawableRequestBuilder uriBuilder(Uri uri, int holderResId) {
        return Glide.with(getContext())
                .load(uri)
                .dontAnimate()
                .centerCrop()
                .crossFade()
                .fallback(holderResId)
                .error(holderResId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
    }

    // 创建 Url DrawableRequestBuilder
    public DrawableRequestBuilder urlBuilder(final String url, int holderResId) {
        if (GlideManager.getInstance().isRequestFailedUrl(url)) {
            return resBuilder(holderResId, holderResId);
        }
        return Glide.with(getContext())
                .load(url)
                .dontAnimate()
                .centerCrop()
                .crossFade()
                .fallback(holderResId)
                .error(holderResId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        GlideManager.getInstance().putRequestFailedUrl(url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                });
    }

    public boolean isGif(String url) {
        if (TextUtils.isEmpty(url)) return false;
        if (url.endsWith(".gif")) return true;
        return false;
    }

}
