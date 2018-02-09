package com.xyz.php.abs.presenters;

/**
 * 2017/10/18.
 */
public interface ILoginPresenter {

    /**
     * 获取上次登录的用户的手机号
     *
     * @return 手机号
     */
    String getLastMobile();

    /**
     * 登录
     *
     * @param mobile   手机号
     * @param password 密码
     */
    void signIn(String mobile, String password);

    /**
     * 验证输入数据
     *
     * @param mobile   手机号
     * @param password 密码
     */
    void validate(String mobile, String password);

}
