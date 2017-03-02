package com.sunfusheng.gank.widget.GildeImageView;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.lang.ref.WeakReference;

/**
 * Created by sunfusheng on 2017/1/23.
 */
public class GlideImageLoader {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String SEPARATOR = "/";
    private WeakReference<ImageView> mImageView;

    public GlideImageLoader(ImageView imageView) {
        mImageView = new WeakReference<ImageView>(imageView);
    }

    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    // 将资源ID转为Uri
    public Uri resIdToUri(int resourceId) {
        if (getImageView() == null) return null;
        return Uri.parse(ANDROID_RESOURCE + getImageView().getContext().getPackageName() + SEPARATOR + resourceId);
    }

    // 加载网络图片
    public void loadNetImage(String url, int holderResId) {
        if (getImageView() == null) return;
        urlBuilder(url, holderResId).into(getImageView());
    }

    // 加载drawable图片
    public void loadResImage(@IdRes int resId, int holderResId) {
        if (getImageView() == null) return;
        resBuilder(resId, holderResId).into(getImageView());
    }

    // 加载本地图片
    public void loadLocalPathImage(String path, int holderResId) {
        if (getImageView() == null) return;
        urlBuilder("file://" + path, holderResId).into(getImageView());
    }

    // 加载网络圆型图片
    public void loadNetCircleImage(String url, int holderResId) {
        if (getImageView() == null) return;
        urlBuilder(url, holderResId)
                .transform(new GlideCircleTransform(getImageView().getContext()))
                .into(getImageView());
    }

    // 加载drawable圆型图片
    public void loadLocalResCircleImage(int resId, int holderResId) {
        if (getImageView() == null) return;
        resBuilder(resId, holderResId)
                .transform(new GlideCircleTransform(getImageView().getContext()))
                .into(getImageView());
    }

    // 加载本地圆型图片
    public void loadLocalPathCircleImage(String path, int holderResId) {
        if (getImageView() == null) return;
        urlBuilder("file://" + path, holderResId)
                .transform(new GlideCircleTransform(getImageView().getContext()))
                .into(getImageView());
    }

    // 创建 Res DrawableRequestBuilder
    public DrawableRequestBuilder resBuilder(int resId, int holderResId) {
        if (getImageView() == null) return null;
        return uriBuilder(resIdToUri(resId), holderResId);
    }

    // 创建 Uri DrawableRequestBuilder
    public DrawableRequestBuilder uriBuilder(Uri uri, int holderResId) {
        if (getImageView() == null) return null;
        return Glide.with(getImageView().getContext())
                .load(uri)
                .crossFade()
                .dontAnimate()
                .fallback(holderResId)
                .error(holderResId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
    }

    // 创建 Url DrawableRequestBuilder
    public DrawableRequestBuilder urlBuilder(final String url, int holderResId) {
        if (getImageView() == null) return null;
        if (GlideManager.getInstance().isFailedUrl(url)) {
            return resBuilder(holderResId, holderResId);
        }
        return Glide.with(getImageView().getContext())
                .load(url)
                .crossFade()
                .dontAnimate()
                .fallback(holderResId)
                .error(holderResId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        GlideManager.getInstance().putFailedUrl(url);
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
