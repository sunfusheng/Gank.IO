package com.sunfusheng.gank.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.util.dialog.ImagesDialog;

/**
 * Created by sunfusheng on 2017/6/1.
 */
public class MoreActionHelper {

    public static void showMoreMenu(View anchor, GankItem gank) {
        Context context = anchor.getContext();
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.item_more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_check_image:
                    new ImagesDialog(context, gank.images).show();
                    return true;
                case R.id.item_copy_url:
                    copy(context, gank.url);
                    return true;
                case R.id.item_share:
                    share(context, gank.desc + "\n" + gank.url);
                    return true;
            }
            return false;
        });
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.item_check_image);
        if (!AppUtil.isEmpty(gank.images)) {
            menuItem.setVisible(true);
            menuItem.setTitle("查看效果图（共" + gank.images.size() + "张）");
        } else {
            menuItem.setVisible(false);
        }
        popupMenu.show();
    }

    // 复制链接
    @SuppressWarnings("deprecation")
    public static void copy(Context context, String content) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setText(content.trim());
        ToastUtil.show(context, "已复制");
    }

    // 系统分享
    public static void share(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }
}
