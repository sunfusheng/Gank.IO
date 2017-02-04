package com.sunfusheng.gank.util.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sunfusheng.gank.R;

/**
 * Created by sunfusheng on 15/8/20.
 */
public class CommonDialog {

    private Context mContext;
    private MaterialDialog materialDialog;

    public CommonDialog(Context context) {
        this.mContext = context;
    }

    public MaterialDialog getMaterialDialog() {
        return materialDialog;
    }

    public void show(String title, String content, String positiveText, String negativeText,
                     MaterialDialog.SingleButtonCallback positiveCallBack,
                     MaterialDialog.SingleButtonCallback negativeCallBack) {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(mContext)
                    .title(title)
                    .content(content)
                    .contentColor(mContext.getResources().getColor(R.color.color3))
                    .canceledOnTouchOutside(false)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .negativeColor(mContext.getResources().getColor(R.color.color3))
                    .onPositive(positiveCallBack)
                    .onNegative(negativeCallBack)
                    .build();
        }
        if (!materialDialog.isShowing()) {
            materialDialog.show();
        }
    }

    public void show(String title, String content, MaterialDialog.SingleButtonCallback positiveCallBack) {
        show(title, content, mContext.getString(R.string.ok), mContext.getString(R.string.cancel), positiveCallBack, null);
    }

    public void show(String title, String content, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveCallBack) {
        show(title, content, positiveText, negativeText, positiveCallBack, null);
    }

    public void dismiss() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
            materialDialog = null;
        }
    }

}
