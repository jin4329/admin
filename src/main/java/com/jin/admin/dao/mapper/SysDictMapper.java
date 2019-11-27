package com.jin.admin.dao.mapper;

import com.jin.admin.common.MyBaseMapper;
import com.jin.admin.model.SysDict;
import com.jin.admin.param.GetDictPageParam;
import com.jin.admin.response.DictData;

import java.util.List;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
public interface SysDictMapper extends MyBaseMapper<SysDict> {
    List<DictData> getDictList(GetDictPageParam param);
}
