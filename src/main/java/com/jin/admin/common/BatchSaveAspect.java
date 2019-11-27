package com.jin.admin.common;

import com.jin.admin.util.ListUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Jin
 * @create 2018-12-27 11:09
 */
@Aspect
@Component
public class BatchSaveAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(* com.jin.admin.dao.repository.BaseRepository.batchInsert(..))")
    public void sign() {
    }

    @Pointcut("execution(* com.jin.admin.dao.repository.BaseRepository.renew(..))")
    public void updateSign() {
    }

    /**
     * @param joinPoint joinPoint
     * @param result    result
     * @return void
     * @author Jin
     * @description jpa批量新增切面
     */
    @AfterReturning(pointcut = "sign()", returning = "result")
    @SneakyThrows
    public void afterReturning(JoinPoint joinPoint, int result) {
        Object[] args = joinPoint.getArgs();
        List<Object> list = (List<Object>) args[0];
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        Class clzz = list.get(0).getClass();
        //获取表名
        String tableName = getTableNameForClass(clzz);
        //所有字段
        Field[] fields = FieldUtils.getAllFields(clzz);
        //getId
        Method getId = clzz.getMethod("getId");
        //拼接sql
        StringBuilder sql = new StringBuilder("insert into ").append(tableName).append("(");
        Column column = null;
        List<Method> fieldGetter = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if ("id".equals(field.getName()) || !ObjectUtils.isEmpty(field.getAnnotation(CreationTimestamp.class)) || !ObjectUtils.isEmpty(field.getAnnotation(UpdateTimestamp.class))) {
                continue;
            }
            // 方法名称
            Method method = clzz.getMethod("get" + StringUtils.capitalize(field.getName()));
            if (ObjectUtils.isEmpty(method)) {
                // isDelete等字段，idea会自动生成getDelete方法
                method = clzz.getMethod("get" + StringUtils.capitalize(field.getName().substring(2)));
            }
            fieldGetter.add(method);
            column = field.getAnnotation(Column.class);
            if (ObjectUtils.isEmpty(column)) {
                sql.append(field.getName().replaceAll("[A-Z]", "_$0").toLowerCase());
            } else {
                sql.append(column.name());
            }
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values");
        String preSql = sql.toString();
        //拼接Value
        List<List<Object>> avgList = ListUtil.averageList(list, 1000);
        if (fields.length > 20 && fields.length < 30) {
            avgList = ListUtil.averageList(list, 200);
        } else if (fields.length >= 30) {
            avgList = ListUtil.averageList(list, 100);
        }
        List<StringBuffer> sqlList = new ArrayList<>(avgList.size());

        // 组装sql
        setInsertSql(avgList, preSql, getId, fieldGetter, sqlList);

        for (StringBuffer s : sqlList) {
            //如果是有ID的  就不执行sql了
            if ("values".equals(s.substring(s.length() - 6))) {
                continue;
            }
            s.deleteCharAt(s.length() - 1).append(";");
            Query q = entityManager.createNativeQuery(s.toString());
            q.executeUpdate();
        }
        entityManager.close();
    }


    /**
     * @param joinPoint joinPoint
     * @param result    result
     * @return void
     * @author Jin
     * @description jpa批量更新切面
     */
    @AfterReturning(pointcut = "updateSign()", returning = "result")
    @SneakyThrows
    public void repositoryBatchUpdateBySelective(JoinPoint joinPoint, int result) {
        Object[] args = joinPoint.getArgs();
        List list = (List) args[0];
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        Class clazz = list.get(0).getClass();
        //获取表名
        String tableName = getTableNameForClass(clazz);
        //所有字段
        Field[] fields = FieldUtils.getAllFields(clazz);
        //getId
        Method getId = clazz.getMethod("getId");

        List<Object> listNeeded = new ArrayList<>();
        boolean flag;
        for (Object o : list) {
            flag = !ObjectUtils.isEmpty(getId.invoke(o));
            if (flag) {
                listNeeded.add(o);
            }
        }
        if (ObjectUtils.isEmpty(listNeeded)) {
            return;
        }
        // 分组，每组1000个，默认一次更新一千个
        List<List<Object>> avgList = ListUtil.averageList(listNeeded, 1000);
        if (fields.length > 20 && fields.length < 30) {
            // 字段在20到30之间时，一次更新200
            avgList = ListUtil.averageList(listNeeded, 200);
        } else if (fields.length >= 30) {
            // 字段大于30时，一次更新30
            avgList = ListUtil.averageList(listNeeded, 30);
        }
        List<String> sqlList = new ArrayList<>(avgList.size());
        StringBuilder sql = new StringBuilder("update ").append(tableName).append(" set ");
        for (List list1 : avgList) {
            // TODO.. 优化
            Set<Object> ids = new HashSet<>(list1.size());
            for (Object o : list1) {
                Object id = getId.invoke(o);
                ids.add(id);
            }
            // 组装sql
            setUpdateSql(sql, fields, clazz, list1, getId, ids, sqlList);
        }
        for (String str : sqlList) {
            Query q = entityManager.createNativeQuery(str);
            q.executeUpdate();
        }
        entityManager.close();
    }

    /**
     * 根据注解获取表名
     */
    private String getTableNameForClass(Class<?> clazz) {
        String tableName;
        Table table = clazz.getAnnotation(Table.class);
        if (null != table) {
            tableName = table.name();
            if ("".equalsIgnoreCase(tableName)) {
                tableName = clazz.getSimpleName();
            }
        } else {
            tableName = clazz.getSimpleName();
        }
        return tableName;
    }

    private void setInsertSql(List<List<Object>> avgList, String preSql, Method getId, List<Method> fieldGetter,
                              List<StringBuffer> sqlList) throws Exception {
        for (List list1 : avgList) {
            StringBuffer sb = new StringBuffer(preSql);
            for (Object obj : list1) {
                Object id = getId.invoke(obj);
                if (!ObjectUtils.isEmpty(id)) {
                    continue;
                }
                sb.append("(");
                for (Method getter : fieldGetter) {
                    Object invoke = getter.invoke(obj);
                    if (invoke instanceof String && invoke != null) {
                        // 是String并且不为null
                        sb.append("\"").append(invoke).append("\"");
                    } else {
                        if (!ObjectUtils.isEmpty(invoke) && !(invoke instanceof Number || invoke instanceof Boolean)) {
                            if (invoke instanceof Date) {
                                invoke = new Timestamp(((Date) invoke).getTime());
                            }
                            sb.append("\"").append(invoke).append("\"");
                        } else {
                            sb.append(invoke);
                        }
                    }

                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("),");
            }
            sqlList.add(sb);
        }
    }

    private void setUpdateSql(StringBuilder sql, Field[] fields, Class clazz, List list1, Method getId, Set<Object> ids,
                              List<String> sqlList) throws Exception {
        StringBuilder sb = new StringBuilder(sql);
        for (Field field : fields) {
            if ("id".equals(field.getName()) || !ObjectUtils.isEmpty(field.getAnnotation(CreationTimestamp.class)) || !ObjectUtils.isEmpty(field.getAnnotation(UpdateTimestamp.class))) {
                continue;
            }
            // 方法名称
            Method method = clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
            if (ObjectUtils.isEmpty(method)) {
                // isDelete等字段，idea会自动生成getDelete方法
                method = clazz.getMethod("get" + StringUtils.capitalize(field.getName().substring(2)));
            }
            Column column = field.getAnnotation(Column.class);
            String columnName;
            if (ObjectUtils.isEmpty(column)) {
                columnName = field.getName().replaceAll("[A-Z]", "_$0").toLowerCase();
            } else {
                columnName = column.name();
            }
            sb.append(columnName).append(" = case id ");
            //拼接Value
            for (Object obj : list1) {
                Object id = getId.invoke(obj);
                sb.append(" when ").append(id).append(" then ");
                // 字段对应的值
                Object o = method.invoke(obj);
                if (null == o) {
                    // 没有值
                    sb.append(columnName);
                } else {
                    if (o instanceof Number || o instanceof Boolean) {
                        // 是Boolean或数字
                        sb.append(o);
                    } else {
                        // 其他

                        if (o instanceof Date) {
                            o = new Timestamp(((Date) o).getTime());
                        }
                        sb.append("\"").append(o).append("\"");
                    }
                }
            }
            sb.append(" end,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" where id in (").append(org.apache.commons.lang3.StringUtils.join(ids, ",")).append(");");
        sqlList.add(sb.toString());
    }
}