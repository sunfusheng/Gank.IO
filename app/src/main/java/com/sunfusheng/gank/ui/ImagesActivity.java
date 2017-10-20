package com.sunfusheng.gank.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.util.ImageHelper;
import com.sunfusheng.gank.widget.PhotoView.HackyViewPager;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.sunfusheng.glideimageview.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by sunfusheng on 2017/1/23.
 */
public class ImagesActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    HackyViewPager viewPager;
    @BindView(R.id.tv_cur_position)
    TextView tvCurPosition;
    @BindView(R.id.tv_separator)
    TextView tvSeparator;
    @BindView(R.id.tv_sum_count)
    TextView tvSumCount;
    @BindView(R.id.rl_indicator)
    RelativeLayout rlIndicator;
    @BindView(R.id.iv_save)
    ImageView ivSave;

    private List<String> images;
    private int curPos = 0;
    private ImageHelper imageHelper;

    public static void startActivity(Context context, ArrayList<String> images) {
        Intent intent = new Intent(context, ImagesActivity.class);
        intent.putStringArrayListExtra("images", images);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, ArrayList<String> images, String curImage) {
        Intent intent = new Intent(context, ImagesActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("curImage", curImage);
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
        if (AppUtil.isEmpty(images)) {
            finish();
        }
        String curImage = getIntent().getStringExtra("curImage");
        curPos = TextUtils.isEmpty(curImage) ? 0 : images.indexOf(curImage);
    }

    private void initView() {
        rlIndicator.setVisibility((images.size() == 1) ? View.GONE : View.VISIBLE);
        tvCurPosition.setText(String.valueOf(curPos + 1));
        tvSumCount.setText(String.valueOf(images.size()));
        viewPager.setAdapter(new PhotoViewAdapter(this, images));
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                curPos = position;
                tvCurPosition.setText(String.valueOf(position + 1));
                viewPager.setTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(curPos);

        AppUtil.singleClick(ivSave, o -> {
            imageHelper = new ImageHelper(mActivity);
            imageHelper.saveImage(images.get(curPos));
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

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_image_layout, null);

            PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            CircleProgressView progressView = (CircleProgressView) view.findViewById(R.id.progressView);

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    mActivity.finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                    mActivity.finish();
                }
            });

            GlideImageLoader imageLoader = GlideImageLoader.create(photoView);
            imageLoader.loadImage(mList.get(position), R.color.transparent);
            imageLoader.setOnGlideImageViewListener(mList.get(position), (percent, isDone, exception) -> {
                progressView.setProgress(percent);
                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            });
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
