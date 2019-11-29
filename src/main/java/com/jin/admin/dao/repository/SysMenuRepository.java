package com.jin.admin.dao.repository;

import com.jin.admin.model.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
@Repository
public interface SysMenuRepository extends BaseRepository<SysMenu, Long>{
    List<SysMenu> getByIdIn(List<Integer> ids);
}
