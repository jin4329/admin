package com.jin.admin.service.admin;

import com.jin.admin.model.BsBasicUserInfo;
import com.jin.admin.response.MenuData;
import com.jin.admin.response.UserInfoData;

import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 18:23
 */
public interface RedisService {
    UserInfoData getBasicUserInfo(Long userId);

    String getToken(Long userId);

    List<MenuData> getMenuList(Long userId);
}
