package com.sunfusheng.gank.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.GankApp;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.util.Utils;
import com.sunfusheng.gank.widget.GildeImageView.GlideImageLoader;
import com.sunfusheng.gank.widget.PhotoView.HackyViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by sunfusheng on 2017/1/23.
 */
public class PhotoViewsActivity extends BaseActivity {

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

    private List<String> mGirls;
    private int curPosition = 0;

    public static void startActivity(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Intent intent = new Intent(context, PhotoViewsActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoviews);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mGirls = GankApp.girls;
        if (Utils.isEmpty(mGirls)) {
            throw new RuntimeException("The girls of gank is empty!");
        }
        curPosition = mGirls.indexOf(getIntent().getStringExtra("url"));
    }

    private void initView() {
        tvCurPosition.setText(String.valueOf(curPosition + 1));
        tvSumCount.setText(String.valueOf(mGirls.size()));
        viewPager.setAdapter(new PhotoViewAdapter(this, mGirls));
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvCurPosition.setText(String.valueOf(position + 1));
                viewPager.setTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(curPosition);
    }

    static class PhotoViewAdapter extends PagerAdapter {

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
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
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

            GlideImageLoader imageLoader = new GlideImageLoader(photoView);
            imageLoader.loadNetImage(mList.get(position), R.mipmap.liuyifei);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return photoView;
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
