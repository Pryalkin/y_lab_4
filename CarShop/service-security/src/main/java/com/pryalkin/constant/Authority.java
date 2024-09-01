package com.pryalkin.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:edit" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:edit", "user:update" };
}
