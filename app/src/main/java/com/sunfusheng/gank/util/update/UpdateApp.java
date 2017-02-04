package com.sunfusheng.gank.util.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.util.Utils;
import com.sunfusheng.gank.util.dialog.CommonDialog;
import com.sunfusheng.gank.util.dialog.DownloadDialog;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * Created by sunfusheng on 2017/2/4.
 */
public class UpdateApp {

    private Context mContext;
    private int lastPos = 0;
    private DownloadDialog downloadDialog;

    private String fileName = "RxGank.apk";
    private String filePath = Environment.getExternalStorageDirectory().getPath();
    private String apkPathName = filePath + File.separator + fileName;

    public UpdateApp(Context context) {
        this.mContext = context;
        downloadDialog = new DownloadDialog(context);
    }

    public void dealWithVersion(final VersionEntity entity) {
        String content = entity.getChangelog() + "\n\n下载(V" + entity.getVersionShort() + ")替换当前版本(" + Utils.getVersionName() + ")?";
        new CommonDialog(mContext).show(
                mContext.getString(R.string.update_app),
                content,
                mContext.getString(R.string.update_rightnow),
                mContext.getString(R.string.update_no),
                (dialog, which) -> {
                    download(entity.getInstall_url());
                });
    }

    public void download(String url) {
        RxDownload.getInstance()
                .download(url, fileName, filePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadStatus>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        downloadDialog.show();
                    }

                    @Override
                    public void onNext(DownloadStatus value) {
                        float progress = (float) (value.getDownloadSize() * 1.0 / value.getTotalSize());
                        int p = (int) (progress * 100);
                        if (p > lastPos) {
                            lastPos = p;
                            downloadDialog.getMaterialDialog().setProgress(p);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadDialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        downloadDialog.dismiss();
                        installPackage();
                    }
                });
    }

    // 安装应用
    private void installPackage() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPathName)), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
