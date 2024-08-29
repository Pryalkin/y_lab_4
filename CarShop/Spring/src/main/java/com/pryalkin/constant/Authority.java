package com.pryalkin.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:edit", "user:decorate" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create_app", "user:edit", "user:decorate" };
}