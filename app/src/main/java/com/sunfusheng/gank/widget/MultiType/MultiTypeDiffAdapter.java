package com.sunfusheng.gank.widget.MultiType;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by sunfusheng on 2017/1/22.
 */
public class MultiTypeDiffAdapter extends MultiTypeAdapter {

    private List<?> oldItems;

    public MultiTypeDiffAdapter(@NonNull List<?> items) {
        super(items);
    }

    public void setItems(List<?> newItems) {
        final DiffCallback diffCallback = new DiffCallback(oldItems, newItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.oldItems.clear();
//        this.oldItems.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    static class DiffCallback extends DiffUtil.Callback {

        private final List<?> oldList;
        private final List<?> newList;

        public DiffCallback(List<?> oldList, List<?> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldItem = oldList.get(oldItemPosition);
            Object newItem = newList.get(newItemPosition);
            return Objects.equals(oldItem, newItem);
        }
    }
}
