package com.xyz.php.entities;

import com.xyz.php.config.BaseEntity;

/**
 * 2017/10/18.
 */
public class UserEntity extends BaseEntity {

    public String id;
    public String avatars;
    public String mobile;
    public String username;
    public String token;

    public int status;          // 10：激活；0：禁用
}
