package com.jin.admin.util;

import com.jin.admin.common.BusinessException;
import com.jin.admin.common.UtilException;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jin
 * @description
 * @date 2019/11/6 13:43
 */
public class ObjectUtil {
    private static final String DEFAULT_PARENT_ID_FIELD_NAME = "parentId";
    private static final String DEFAULT_ID_FIELD_NAME = "id";
    private static final String DEFAULT_CHILDREN_IDS_FIELD_NAME = "children";
    private static final String DEFAULT_CODE = "0";
    private static final String DEFAULT_MSG = "数据不存在！";

    /**
     * @param o
     * @return boolean
     * @author Jin
     * @description 判断o是否为空，以下情况返回true
     * 1.o = null
     * 2.o为CharSequence，并且length = 0
     * 3.o为collection，并且o.size() = 0
     * 4.o为map，并且长度为0
     * 5.o为Optional，并且所含内容为null
     */
    public static boolean isEmpty(Object o) {
        return ObjectUtils.isEmpty(o);
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * @param dataList 数据
     * @param parentId 父id
     * @return java.util.Collection<T>
     * @author Jin
     * @description 由上至下组装无限层级数据
     * 默认数据包含id字段|父id字段（parentId）|子级数据字段（children）
     * children数据类型应同dataList，否则抛出异常
     */
    public static <T> Collection<T> buildDataWithChildren(Collection<T> dataList, Object parentId) {
        return buildDataWithChildren(dataList, parentId, DEFAULT_ID_FIELD_NAME, DEFAULT_PARENT_ID_FIELD_NAME, DEFAULT_CHILDREN_IDS_FIELD_NAME);
    }

    /**
     * @param dataList          数据
     * @param parentId          父id
     * @param childrenFieldName 用于存储数据的子级数据的字段，常见为children，其数据类型应同dataList，否则抛出异常
     * @return java.util.Collection<T>
     * @author Jin
     * @description 由上至下组装无限层级数据
     * 默认数据包含id字段|父id字段（parentId）
     */
    public static <T> Collection<T> buildDataWithChildren(Collection<T> dataList, Object parentId, String childrenFieldName) {
        return buildDataWithChildren(dataList, parentId, DEFAULT_ID_FIELD_NAME, DEFAULT_PARENT_ID_FIELD_NAME, childrenFieldName);
    }

    /**
     * @param dataList          数据
     * @param parentId          父id
     * @param parentFieldName   用于数据定位父级的字段，与父级的keyFieldName相对应
     * @param childrenFieldName 用于存储数据的子级数据的字段，常见为children，其数据类型应同dataList，否则抛出异常
     * @return java.util.Collection<T>
     * @author Jin
     * @description 由上至下组装无限层级数据
     * 默认数据包含id字段
     */
    public static <T> Collection<T> buildDataWithChildren(Collection<T> dataList, Object parentId, String parentFieldName, String childrenFieldName) {
        return buildDataWithChildren(dataList, parentId, DEFAULT_ID_FIELD_NAME, parentFieldName, childrenFieldName);
    }

    /**
     * @param dataList          数据
     * @param parentId          父id
     * @param keyFieldName      用于数据定位的唯一字段
     * @param parentFieldName   用于数据定位父级的字段，与父级的keyFieldName相对应
     * @param childrenFieldName 用于存储数据的子级数据的字段，常见为children，其数据类型应同dataList，否则抛出异常
     * @return java.util.List<T>
     * @author Jin
     * @description 由上至下组装无限层级数据
     */
    public static <T> Collection<T> buildDataWithChildren(Collection<T> dataList, Object parentId, String keyFieldName, String parentFieldName, String childrenFieldName) {
        if (isEmpty(dataList)) {
            return new ArrayList<>();
        }
        if (isEmpty(parentFieldName)) {
            parentFieldName = DEFAULT_PARENT_ID_FIELD_NAME;
        }

        Iterator<T> iterator = dataList.iterator();
        T dataFirst = iterator.next();
        // getParentId()
        Method parentIdMethod = ReflectUtils.getAccessibleMethod(dataFirst, ReflectUtils.getterMethodName(parentFieldName));
        if (isEmpty(parentIdMethod)) {
            throw new UtilException("调用cn.com.wtrj.ms.commercial.utils.ObjectUtil.buildDataWithChildren 错误，反射获取parentId的getter方法出错！");
        }

        Collection<T> result;
        if (!isEmpty(parentId)) {
            // parentId is not null
            result = dataList.stream().filter(x -> {
                try {
                    return parentId.equals(parentIdMethod.invoke(x));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }).collect(Collectors.toList());
        } else {
            // parentId is null
            result = dataList.stream().filter(x -> {
                try {
                    return parentIdMethod.invoke(x) == null;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }).collect(Collectors.toList());
        }

        if (isEmpty(result)) {
            return result;
        }

        // getId()
        if (isEmpty(keyFieldName)) {
            keyFieldName = DEFAULT_ID_FIELD_NAME;
        }
        Method idMethod = ReflectUtils.getAccessibleMethod(dataFirst, ReflectUtils.getterMethodName(keyFieldName));
        if (isEmpty(idMethod)) {
            throw new UtilException("调用cn.com.wtrj.ms.commercial.utils.ObjectUtil.buildDataWithChildren 错误，反射获取id的getter方法出错！");
        }

        Class childrenSetterParameterClass = getDataCollectionClass(dataList);

        String name = childrenSetterParameterClass.getName();

        // setChildren(Collection param)
        if (isEmpty(childrenFieldName)) {
            childrenFieldName = DEFAULT_CHILDREN_IDS_FIELD_NAME;
        }
        Method childrenSetterMethod = ReflectUtils.getAccessibleMethod(dataFirst, ReflectUtils.setterMethodName(childrenFieldName), childrenSetterParameterClass);
        if (isEmpty(childrenSetterMethod)) {
            throw new UtilException("调用cn.com.wtrj.ms.commercial.utils.ObjectUtil.buildDataWithChildren 错误，反射获取children的setter方法出错！");
        }

        for (T data : result) {
            // 获取id对应的值
            Object id = null;
            try {
                id = idMethod.invoke(data);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            // 子级数据
            Collection<T> children = buildDataWithChildren(dataList, id, keyFieldName, parentFieldName, childrenFieldName);
            if (name.contains("Collection")) {
                ReflectUtils.invokeMethod(data, childrenSetterMethod, new Object[]{children});
            } else {
                if (name.contains("Set")) {
                    ReflectUtils.invokeMethod(data, childrenSetterMethod, new Object[]{new HashSet<>(children)});
                } else {
                    ReflectUtils.invokeMethod(data, childrenSetterMethod, new Object[]{new ArrayList<>(children)});
                }
            }

        }

        return result;
    }

    private static Class getDataCollectionClass(Collection dataList) {
        if (dataList instanceof List) {
            return List.class;
        } else if (dataList instanceof Set) {
            return Set.class;
        }
        return Collection.class;
    }

    /**
     * @param obj
     * @param code 异常code
     * @param msg  异常msg
     * @return void
     * @author Jin
     * @description 如果obj为null或者空，则抛出异常
     */
    public static void emptyThrowException(Object obj, String code, String msg) {
        if (isEmpty(obj)) {
            throw new BusinessException(code, msg);
        }
    }

    public static void emptyThrowException(Object obj, String msg) {
        if (isEmpty(obj)) {
            throw new BusinessException(DEFAULT_CODE, msg);
        }
    }

    public static void emptyThrowException(Object obj) {
        if (isEmpty(obj)) {
            throw new BusinessException(DEFAULT_CODE, DEFAULT_MSG);
        }
    }
//    public static void main(String[] args) {
//        Collection<IdNameDataWithChildren> dataList = new HashSet<>();
//        dataList.add(new IdNameDataWithChildren(1, null));
//        dataList.add(new IdNameDataWithChildren(2, null));
//        dataList.add(new IdNameDataWithChildren(3, null));
//        dataList.add(new IdNameDataWithChildren(5, 1));
//        dataList.add(new IdNameDataWithChildren(6, 5));
//        dataList.add(new IdNameDataWithChildren(7, 5));
//        dataList.add(new IdNameDataWithChildren(8, 6));
//        dataList.add(new IdNameDataWithChildren(9, 1));
//        dataList.add(new IdNameDataWithChildren(10, 7));
//        dataList.add(new IdNameDataWithChildren(4, null));
//        Collection<IdNameDataWithChildren> idNameDataWithChildren = buildDataWithChildren(dataList, null, "id", "parentId", "children");
//        System.out.println(idNameDataWithChildren);
//    }
}
