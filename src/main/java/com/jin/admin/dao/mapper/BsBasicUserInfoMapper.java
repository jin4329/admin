package com.jin.admin.dao.mapper;

import com.jin.admin.common.MyBaseMapper;
import com.jin.admin.model.BsBasicUserInfo;
import com.jin.admin.response.UserInfoData;

/**
 * 
 * @author Jin 
 * @create 2019-11-27
 */
public interface BsBasicUserInfoMapper extends MyBaseMapper<BsBasicUserInfo> {
    UserInfoData getUserInfoData(Long userId);
}
