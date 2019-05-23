package com.sunfusheng.gank.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.sunfusheng.GlideImageLoader;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.util.CollectionUtil;
import com.sunfusheng.gank.util.ImageHelper;
import com.sunfusheng.gank.util.ViewUtil;
import com.sunfusheng.gank.widget.PhotoView.HackyViewPager;
import com.sunfusheng.gank.widget.RadiusWidget.RadiusButton;
import com.sunfusheng.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author by sunfusheng on 2017/1/23.
 */
public class ImagesActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    HackyViewPager viewPager;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.btn_save)
    RadiusButton btnSave;

    private List<String> images;
    private int curIndex;
    private ImageHelper imageHelper;

    public static void open(Context context, String image) {
        if (TextUtils.isEmpty(image)) return;
        ArrayList<String> images = new ArrayList<>();
        images.add(image);
        open(context, images, 0);
    }

    public static void open(Context context, ArrayList<String> images) {
        open(context, images, 0);
    }

    public static void open(Context context, ArrayList<String> images, String curImage) {
        if (CollectionUtil.isEmpty(images)) return;
        open(context, images, images.indexOf(curImage));
    }

    public static void open(Context context, ArrayList<String> images, int curIndex) {
        if (CollectionUtil.isEmpty(images)) return;
        Intent intent = new Intent(context, ImagesActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("curIndex", curIndex);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        images = getIntent().getStringArrayListExtra("images");
        curIndex = getIntent().getIntExtra("curIndex", 0);
    }

    private void initView() {
        refreshIndex(curIndex);
        viewPager.setAdapter(new PhotoViewAdapter(this, images));
    }

    private void refreshIndex(int index) {
        curIndex = index;
        tvIndex.setVisibility(images.size() == 1 ? View.GONE : View.VISIBLE);
        tvIndex.setText((index + 1) + "/" + images.size());

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                refreshIndex(position);
                viewPager.setTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(curIndex);

        ViewUtil.singleClick(btnSave, o -> {
            imageHelper = new ImageHelper(mActivity);
            imageHelper.saveImage(images.get(curIndex));
        });
    }

    @Override
    protected void onDestroy() {
        if (imageHelper != null) {
            imageHelper.unInit();
        }
        super.onDestroy();
    }

    public static class PhotoViewAdapter extends PagerAdapter {

        private Activity mActivity;
        private List<String> mList;

        public PhotoViewAdapter(Activity activity, List<String> list) {
            this.mActivity = activity;
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_image_layout, null);

            PhotoView photoView = view.findViewById(R.id.photoView);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            CircleProgressView progressView = view.findViewById(R.id.progressView);

            photoView.setOnPhotoTapListener((view1, x, y) -> mActivity.finish());

            GlideImageLoader imageLoader = GlideImageLoader.create(photoView);
//            imageLoader.loadImage(mList.get(position), R.color.transparent);
//            imageLoader.setOnGlideImageViewListener(mList.get(position), (percent, isDone, exception) -> {
//                progressView.setProgress(percent);
//                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
//            });
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
