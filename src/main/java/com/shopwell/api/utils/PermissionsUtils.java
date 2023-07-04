package com.shopwell.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsUtils {
    public static final String ADMIN_CREATE = "ADMIN_CREATE";

    public static final String ADMIN_READ = "ADMIN_READ";

    public static final String ADMIN_UPDATE = "ADMIN_UPDATE";

    public static final String ADMIN_DELETE = "ADMIN_DELETE";

    public static final String USER_CREATE = "USER_CREATE";

    public static final String USER_READ = "USER_READ";

    public static final String USER_UPDATE = "USER_UPDATE";

    public static final String USER_DELETE = "USER_DELETE";

    private String permission;
}
