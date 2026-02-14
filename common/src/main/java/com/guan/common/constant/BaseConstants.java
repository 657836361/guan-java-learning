package com.guan.common.constant;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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


    public static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>()
    );
}
