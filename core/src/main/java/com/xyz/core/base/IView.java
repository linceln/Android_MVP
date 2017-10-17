package com.xyz.core.base;

/**
 * 2017/10/17.
 */

public interface IView<T extends BaseEntity> {

    void initUI();

    void onRequestSuccess(T t);

    void onRequestFail(String msg);
}
