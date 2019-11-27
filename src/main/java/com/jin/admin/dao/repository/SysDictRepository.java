package com.jin.admin.dao.repository;

import com.jin.admin.model.SysDict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
@Repository
public interface SysDictRepository extends BaseRepository<SysDict, Long>{
    List<SysDict> getByPidIsNull();
}
