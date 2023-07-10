package com.shopwell.api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static final String IMAGE_PLACEHOLDER_URL = "https://www.pngitem.com/middle/TiooiRo_avatar-dummy-png-transparent-png/";

    public static <T> T getAuthenticatedUser(Class<T> type){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && type.isAssignableFrom(authentication.getPrincipal().getClass())) {
            return type.cast(authentication.getPrincipal());
        }
        return null;
    }
}
