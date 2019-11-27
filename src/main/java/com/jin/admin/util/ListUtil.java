package com.jin.admin.util;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    /**
     * 将list分组
     *
     * @param source 原list
     * @param n      每组容量
     * @return java.util.List<java.util.List<T>>
     * @author Jin
     */
    public static <T> List<List<T>> averageList(List<T> source, Integer n) {
        Assert.notNull(n, "组数不能为NULL");
        Assert.notNull(source, "数据源不能为不能为NULL");

        // 求余数
        int yushu = source.size() % n;
        // 求分组数
        int count = yushu == 0 ? source.size() / n : source.size() / n + 1;
        List<List<T>> data = new ArrayList<>();
        // 遍历list到余数前
        for (int i = 0; i < count ; i++) {
            List<T> ls;
            if (i < count - 1){
                ls = new ArrayList<>(source.subList(i * n, i * n + n));
            } else {
                // 最后一组
                ls = new ArrayList<>(source.subList(source.size() - yushu, source.size()));
            }
            data.add(ls);
        }
        return data;
    }

}