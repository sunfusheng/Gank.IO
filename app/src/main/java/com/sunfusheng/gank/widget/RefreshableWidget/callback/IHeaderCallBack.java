package com.sunfusheng.gank.widget.RefreshableWidget.callback;

/**
 * 提供自定义headerview的接口
 */
public interface IHeaderCallBack {

    /**
     * 正常状态
     */
    void onStateNormal();

    /**
     * 准备刷新
     */
    void onStateReady();

    /**
     * 正在刷新
     */
    void onStateRefreshing();

    /**
     * 刷新结束
     *
     * @param success 是否刷新成功 success参数由XRefreshView.stopRefresh(boolean)传入
     */
    void onStateFinish(boolean success);

    /**
     * 获取headerview显示的高度与headerview高度的比例
     *
     * @param headerMovePercent 移动距离和headerview高度的比例
     * @param offsetY           headerview移动的距离
     */
    void onHeaderMove(double headerMovePercent, int offsetY, int deltaY);

    /**
     * 隐藏footerview
     */
    void hide();

    /**
     * 显示footerview
     */
    void show();

    /**
     * 获得headerview的高度,如果不想headerview全部被隐藏，就可以只返回一部分的高度
     *
     * @return
     */
    int getHeaderHeight();
}