package com.sunfusheng.gank.base.RecyclerView;

import com.sunfusheng.gank.widget.RefreshLayout.IDragDistanceConverter;

public class DragDistanceConverterEg implements IDragDistanceConverter {

    @Override
    public float convert(float scrollDistance, float refreshDistance) {
        return scrollDistance * 0.5f;
    }
}
