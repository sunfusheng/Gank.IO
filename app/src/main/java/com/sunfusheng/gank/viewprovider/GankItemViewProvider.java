package com.sunfusheng.gank.viewprovider;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.ui.WebViewActivity;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.util.ToastUtil;
import com.sunfusheng.gank.widget.MultiType.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankItemViewProvider extends ItemViewProvider<GankItem, GankItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankItem item) {
        if (TextUtils.isEmpty(item.who)) {
            holder.tvDesc.setText(item.desc);
        } else {
            int start = item.desc.length();
            int end = start + item.who.length() + 3;
            int color = holder.tvDesc.getContext().getResources().getColor(R.color.md_grey_400);
            SpannableStringBuilder ssb = new SpannableStringBuilder(item.desc + " - " + item.who);
            ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new RelativeSizeSpan(0.85f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvDesc.setText(ssb);
        }

        AppUtil.singleClick(holder.rlGank, o -> {
            WebViewActivity.startActivity(holder.tvDesc.getContext(), item.url);
        });

        holder.rlGank.setOnLongClickListener(v -> true);

        holder.ivMore.setOnClickListener(v -> showMoreMenu(holder.ivMore, item));
    }

    private void showMoreMenu(View anchor, GankItem gank) {
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.item_more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_check_image:
                    ToastUtil.show(anchor.getContext(), "开发中...");
                    return true;
                case R.id.item_copy_url:
                    copy(anchor.getContext(), gank.url);
                    return true;
                case R.id.item_share:
                    share(anchor.getContext(), gank.desc + "\n" + gank.url);
                    return true;
            }
            return false;
        });
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.item_check_image);
        menuItem.setVisible(!AppUtil.isEmpty(gank.images));
        popupMenu.show();
    }

    public static void copy(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_gank)
        RelativeLayout rlGank;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
