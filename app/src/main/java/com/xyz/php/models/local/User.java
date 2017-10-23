package com.xyz.php.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * 17/10/2017
 */

public class User extends RealmObject {

    @PrimaryKey
    public int id;

    @Required
    public String mobile;

    @Required
    public String nickname;

    @Required
    public String token;
    public long updated_at;
}
