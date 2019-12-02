package com.jin.admin.service.admin;

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

    String generateToken(Long userId);

    /**
     * @param key
     * @return
     * @author Jin
     * @description 删除缓存
     */
    void remove(String key);

    /**
     * @param preKey 以preKey开头的key
     * @return
     * @author Jin
     * @description 删除以preKey开头key的缓存
     */
    void removeByPre(String preKey);
}
