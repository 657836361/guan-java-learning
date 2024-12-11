package com.guan.learning.common.constant;

import java.util.regex.Pattern;

public class BaseConstants {
    public static final String HTTP_HEADER_AUTH_NAME = "Authorization";
    public static final String LOG_TRACE = "tracerId";
    public static final String REFERER = "Referer";

    /**
     * example123@example.com-> ****@****.com
     */
    public static final Pattern EMAIL_DESENSITIVE_REGEX =
            Pattern.compile("^([a-zA-Z0-9]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,6})$");
}
