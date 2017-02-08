package com.sunfusheng.gank.util;

import android.content.Context;
import android.text.TextUtils;

import com.sunfusheng.gank.util.dialog.LoadingDialog;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by sunfusheng on 2017/2/8.
 */
public class ImageHelper {

    private Context mContext;
    private LoadingDialog mDialog;
    private Disposable mDisposable;
    private String imagePath;

    public ImageHelper(Context context) {
        this.mContext = context;
        imagePath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    }

    public void unInit() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    // 保存图片
    public void saveImage(String imageUrl) {
        Observable.just(imageUrl)
                .filter(it -> !TextUtils.isEmpty(it))
                .map(this::getImageName)
                .filter(it -> !TextUtils.isEmpty(it))
                .filter(it -> !isImageExist(it))
                .subscribe(it -> {
                    RxDownload.getInstance()
                            .download(imageUrl, it, imagePath)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DownloadStatus>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    mDisposable = d;
                                    mDialog = new LoadingDialog(mContext);
                                    mDialog.show("下载图片中...");
                                }

                                @Override
                                public void onNext(DownloadStatus value) {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    mDialog.dismiss();
                                    ToastUtil.show("保存失败");
                                }

                                @Override
                                public void onComplete() {
                                    mDialog.dismiss();
                                    ToastUtil.show("保存成功");
                                }
                            });
                });
    }

    // 获取图片名称
    public String getImageName(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    // 判断图片是否存在
    private boolean isImageExist(String fileName) {
        File file = new File(imagePath, fileName);
        boolean isExist = file.exists();
        if (isExist) {
            ToastUtil.show("图片已存在");
        }
        return isExist;
    }
}
