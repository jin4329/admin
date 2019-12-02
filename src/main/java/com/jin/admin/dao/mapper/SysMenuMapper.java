package com.jin.admin.dao.mapper;

import com.jin.admin.common.MyBaseMapper;
import com.jin.admin.model.SysMenu;
import com.jin.admin.response.MenuData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jin
 * @create 2019-11-27
 */
public interface SysMenuMapper extends MyBaseMapper<SysMenu> {
    List<MenuData> getMenuByUserId(@Param("userId") Long userId);
}
