package com.xyz.php.config;

public class BaseEntity {

    /**
     * 网络请求状态：0：失败；1：成功
     */
    public int code = 1;

    /**
     * 提示信息
     */
    public String message = "请求数据成功";

    /**
     * 错误信息
     */
    public String error = "请求数据失败";
}
