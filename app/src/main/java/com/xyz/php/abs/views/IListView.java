package com.xyz.php.abs.views;

/**
 * 列表类页面的通用接口
 * 2018/2/9.
 */
public interface IListView {

    /**
     * 请求成功后回调，更新页码
     *
     * @param page 页码
     */
    void onPageChange(int page);

    /**
     * 判断是否需要关闭上拉加载
     *
     * @param isPaginateEnabled 是否关闭
     */
    void onWhetherFinishPagination(boolean isPaginateEnabled);

    /**
     * 数据为空时回调
     */
    void onEmptyData();

    /**
     * 网络错误时回调
     */
    void onNetworkError();
}
