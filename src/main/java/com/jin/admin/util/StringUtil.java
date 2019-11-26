package com.jin.admin.util;

import org.springframework.util.StringUtils;

/**
 * @author Jin
 * @description
 * @date 2019/11/26 14:50
 */
public class StringUtil {
    private static final String SPLIT = ",";

    public static String[] split(String str, String split) {
        if (ObjectUtil.isEmpty(str)) {
            return new String[]{};
        }
        if (ObjectUtil.isEmpty(split)) {
            split = SPLIT;
        }
        return StringUtils.split(str, split);
    }

    public static String[] split(String str) {
        return split(str, SPLIT);
    }
}
