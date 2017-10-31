package com.xyz.php.entities;

import com.xyz.php.config.BaseEntity;

import java.util.List;

/**
 * 2017/10/23.
 */
public class UserListEntity extends BaseEntity {
    public int pages;
    public List<UserEntity> user;
}
