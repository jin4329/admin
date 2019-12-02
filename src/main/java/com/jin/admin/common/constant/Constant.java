package com.jin.admin.common.constant;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 17:43
 */
public class Constant {

    /**
     * @description jwt相关
     */
    public static class Jwt{
        public static final String SECRET = "jinssecret";
        public static final String ISSUER = "Jin";
        public static final String NAME = "token";
        public static final Integer EXPIRE_AT = 3600 * 24 * 30;
    }

    public static class RedisKey {
        public static final String USER_INFO = "user_info_%s";
        public static final String USER_TOKEN = "user_token_%s";
        public static final String USER_MENU = "user_menu_%s";
    }

}
