package com.mcargo.common.auth.context;


public class UserContextHolder {
    private static final ThreadLocal<UserContext> holder = new ThreadLocal<>();

    public static void set(UserContext ucon) {
        holder.set(ucon);
    }

    public static UserContext get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
