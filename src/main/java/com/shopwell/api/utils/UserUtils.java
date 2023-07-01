package com.shopwell.api.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static String GetUserLogIn(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
