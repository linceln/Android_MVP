package com.xyz.php.abs.presenters;

/**
 * 18/10/2017
 */
public interface IRegisterPresenter {

    /**
     * 注册
     *
     * @param username       用户名
     * @param mobile         手机号
     * @param password       密码
     * @param repeatPassword 重复密码
     */
    void register(String username, String mobile, String password, String repeatPassword);

    /**
     * 验证输入内容
     *
     * @param username       用户名
     * @param mobile         手机号
     * @param password       密码
     * @param repeatPassword 重复密码
     * @return 是否验证通过
     */
    void validate(String username, String mobile, String password, String repeatPassword);
}
