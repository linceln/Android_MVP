package com.xyz.php.models.db;

import org.litepal.crud.DataSupport;

/**
 * 17/10/2017
 */

public class User extends DataSupport{
    public int id;
    public String avatars;
    public String mobile;
    public String username;
    public String token;
    public long updated_at;
}