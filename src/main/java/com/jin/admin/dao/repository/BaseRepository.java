package com.jin.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jin
 * @description @NoRepositoryBean一般用作父类的repository，有这个注解，spring不会去实例化该repository
 * @date 2019/7/15 15:31
 */
@NoRepositoryBean
public interface BaseRepository<T, PK extends Serializable> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {
    /**
     * JPA批量插入
     *
     * @param list 待保存的集合
     * @return int
     * @deprecated 注意：若对象的Id有值，则该对象不会执行任何操作；调用此方法的service必须开始事务控制
     */
    @Modifying
    default int batchInsert(List<T> list) {
        return 0;
    }

    /**
     * 动态批量更新，字段值是null则使用之前的值，调用此方法的service必须开始事务控制，必须有自增主键id
     *
     * @param list 有id的才会更新
     * @return int
     */
    @Modifying
    default int renew(List<T> list) {
        return 0;
    }
}
