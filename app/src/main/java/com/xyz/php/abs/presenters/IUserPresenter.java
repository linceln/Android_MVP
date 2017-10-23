package com.xyz.php.abs.presenters;

import com.xyz.php.entities.UserEntity;

import java.util.List;

/**
 * 2017/10/23.
 */

public interface IUserPresenter {

    List<UserEntity> getUsers();
}
