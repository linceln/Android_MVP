package com.xyz.php.abs.presenters;

import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.db.User;

import java.util.List;

/**
 * 2017/10/23.
 */

public interface IUserPresenter {

    List<UserEntity> getUserList();

    String getSignedInUsername();

    void clear();

    void getUser(int page);
}
