package com.sunfusheng.gank.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.adapter.RecyclerListAdapter;
import com.sunfusheng.gank.base.BaseListFragment;
import com.sunfusheng.gank.base.RecyclerView.DragDistanceConverterEg;
import com.sunfusheng.gank.model.TestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 2017/1/12.
 */

public class TestFragment extends BaseListFragment<TestEntity> {

    private static final int REQUEST_DURATION = 800;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final List<TestEntity> mItemList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 1; i <= 20; i++) {
            mItemList.add(new TestEntity("青蛙" + i + "号"));
        }
        getOriginAdapter().setItemList(mItemList);
        getHeaderAdapter().notifyDataSetChanged();

        getRecyclerRefreshLayout().setDragDistanceConverter(new DragDistanceConverterEg());

    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @NonNull
    @Override
    public RecyclerListAdapter createAdapter() {
        return new RecyclerListAdapter() {
            {
                addViewType(TestEntity.class, ItemViewHolder::new);
            }
        };
    }

    @Override
    protected InteractionListener createInteraction() {
        return new ItemInteractionListener();
    }

    private void simulateNetworkRequest(final RequestListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(REQUEST_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isAdded()) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<TestEntity> list = new ArrayList<>();
                            for (int i = 1; i <= 20; i++) {
                                list.add(new TestEntity("青蛙" + i + "号"));
                            }
                            listener.onSuccess(list);
                        }
                    });
                }
            }
        }).start();
    }

    private interface RequestListener {
        void onSuccess(List<TestEntity> openProjectModels);

        void onFailed();
    }

    private class ItemInteractionListener extends InteractionListener {
        @Override
        public void requestRefresh() {
            simulateNetworkRequest(new RequestListener() {
                @Override
                public void onSuccess(List<TestEntity> openProjectModels) {
                    mItemList.clear();
                    mItemList.addAll(openProjectModels);
                    getHeaderAdapter().notifyDataSetChanged();
                    ItemInteractionListener.super.requestRefresh();
                }

                @Override
                public void onFailed() {
                    ItemInteractionListener.super.requestFailure();
                }
            });
        }

        @Override
        public void requestMore() {
            simulateNetworkRequest(new RequestListener() {
                @Override
                public void onSuccess(List<TestEntity> openProjectModels) {
                    mItemList.addAll(openProjectModels);
                    getHeaderAdapter().notifyDataSetChanged();
                    ItemInteractionListener.super.requestMore();
                }

                @Override
                public void onFailed() {
                    ItemInteractionListener.super.requestFailure();
                }
            });
        }
    }

    private class ItemViewHolder extends RecyclerListAdapter.ViewHolder<TestEntity> {
        private final TextView tvDesc;


        public ItemViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(getActivity()).inflate(R.layout.layout_test_item, parent, false));
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
        }

        @Override
        public void bind(final TestEntity item, int position) {
            tvDesc.setText(item.desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), item.desc, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
