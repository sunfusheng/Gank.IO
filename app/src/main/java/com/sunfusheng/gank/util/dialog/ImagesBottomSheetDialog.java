package com.sunfusheng.gank.util.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.util.DensityUtil;
import com.sunfusheng.gank.util.StatusBarUtil;
import com.sunfusheng.gank.widget.GildeImageView.GlideImageView;

import java.util.List;

/**
 * Created by sunfusheng on 2017/5/16.
 */
public class ImagesBottomSheetDialog {

    BottomSheetBehavior mBehavior;
    BottomSheetDialog mDialog;

    public void initBottomSheetDialog(Context context, List<String> images) {
        if (mDialog == null) {
            mDialog = new BottomSheetDialog(context);
            View contentView = View.inflate(context, R.layout.bottom_sheet_images, null);
            mDialog.setContentView(contentView);
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.height = DensityUtil.getScreenHeight(context);
            contentView.setLayoutParams(layoutParams);
            GlideImageView imageView = (GlideImageView) contentView.findViewById(R.id.iv_image);
            imageView.loadNetImage(images.get(0), R.color.transparent);
            View parent = (View) contentView.getParent();
            mBehavior = BottomSheetBehavior.from(parent);
            mBehavior.setPeekHeight(layoutParams.height / 2);
            mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    public void show() {
        if (mBehavior != null) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (mDialog != null) {
            mDialog.show();
            StatusBarUtil.setStatusBarTranslucent(mDialog.getOwnerActivity(), true);
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
