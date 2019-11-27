package com.jin.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Jin
 * @description 枚举工具类
 * @date 2019/6/27 11:34
 */
@Slf4j
public class EnumUtil {
    private final static String GET_METHOD_NAME = "getCode";

    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, Object code) {
        Assert.notNull(code, "枚举类对应码不能为空");
        return work(enumClass, String.valueOf(code), GET_METHOD_NAME);
    }

    /**
     * @param enumClass      枚举类class
     * @param code           枚举的code
     * @param codeMethodName code的getter方法名
     * @return E
     * @author Jin
     * @description 通过code获取枚举
     */
    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, Object code, String codeMethodName) {
        Assert.notNull(code, "枚举类对应码不能为空");
        return work(enumClass, String.valueOf(code), codeMethodName);
    }

    public static <E extends Enum<E>> Map<String, E> getEnum(final Class<E> enumClass, Collection<Object> codeCollection) {
        return getEnum(enumClass, codeCollection, GET_METHOD_NAME);
    }

    /**
     * @param enumClass      枚举类class
     * @param codeCollection 枚举的code集合
     * @param codeMethodName code的getter方法名
     * @return java.util.Map<java.lang.String, E>
     * @author Jin
     * @description 返回一个map，key为code，value为枚举。当code集合是空时，返回空map
     */
    public static <E extends Enum<E>> Map<String, E> getEnum(final Class<E> enumClass, Collection<Object> codeCollection, String codeMethodName) {
        if (ObjectUtils.isEmpty(codeCollection)) {
            return new HashMap<>();
        }
        Map<String, E> result = new HashMap<>(codeCollection.size());
        new HashSet<>(codeCollection).forEach(code -> {
            if (ObjectUtils.isEmpty(code)) {
                return;
            }
            result.put(String.valueOf(code), work(enumClass, String.valueOf(code), codeMethodName));
        });
        return result;
    }

    public static <E extends Enum<E>> Map<String, E> getEnum(final Class<E> enumClass, Object[] codeArr, String codeMethodName) {
        return getEnum(enumClass, Arrays.asList(codeArr), codeMethodName);
    }

    public static <E extends Enum<E>> Map<String, E> getEnum(final Class<E> enumClass, Object[] codeArr) {
        return getEnum(enumClass, Arrays.asList(codeArr), GET_METHOD_NAME);
    }

    private static <E extends Enum<E>> E work(final Class<E> enumClass, final String code, String codeMethodName) {
        Assert.notNull(enumClass, "枚举类不能为空");
        Assert.notNull(codeMethodName, "枚举类对应码方法名不能为空");

        try {
            E[] objects = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod(codeMethodName);
            for (E obj : objects) {
                Object invoke = getCode.invoke(obj);
                if (String.valueOf(invoke).equals(code)) {
                    return obj;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        String[] str = new String[]{"member", "administrator", ""};
//        Map<String, CommonTypeEnum.GroupMemberRoleType> anEnum = getEnum(CommonTypeEnum.GroupMemberRoleType.class, str);
//        System.out.println(anEnum.size());
//    }
}
