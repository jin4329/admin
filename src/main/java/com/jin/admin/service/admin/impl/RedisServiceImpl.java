package com.jin.admin.service.admin.impl;

import com.jin.admin.common.constant.Constant;
import com.jin.admin.common.exception.BusinessException;
import com.jin.admin.common.exception.UtilException;
import com.jin.admin.dao.mapper.BsBasicUserInfoMapper;
import com.jin.admin.dao.mapper.SysMenuMapper;
import com.jin.admin.dao.repository.SysMenuRepository;
import com.jin.admin.model.SysMenu;
import com.jin.admin.response.MenuData;
import com.jin.admin.response.UserInfoData;
import com.jin.admin.service.admin.RedisService;
import com.jin.admin.util.JWTUtil;
import com.jin.admin.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 18:26
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {
    private static final Integer DEFAULT_LIST_START = 0;
    private static final Integer DEFAULT_LIST_END = -1;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BsBasicUserInfoMapper bsBasicUserInfoMapper;
    @Autowired
    private SysMenuRepository sysMenuRepository;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserInfoData getBasicUserInfo(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        UserInfoData userInfo = (UserInfoData) redisTemplate.opsForValue().get(String.format(Constant.RedisKey.USER_INFO, userId));
        if (ObjectUtil.isEmpty(userInfo)) {
            log.info("缓存不存在！");
            // 从数据库拿
            userInfo = bsBasicUserInfoMapper.getUserInfoData(userId);
            if (ObjectUtil.isEmpty(userInfo)) {
                throw new BusinessException("用户不存在");
            }
            redisTemplate.opsForValue().set(String.format(Constant.RedisKey.USER_INFO, userId), userInfo);
        }
        return userInfo;
    }

    @Override
    public String getToken(Long userId) {
        return (String) redisTemplate.opsForValue().get(String.format(Constant.RedisKey.USER_TOKEN, userId));
    }

    @Override
    public List<MenuData> getMenuList(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return new ArrayList<>();
        }
        List<MenuData> result = getRedisListsAll(String.format(Constant.RedisKey.USER_MENU, userId));
        if (ObjectUtil.isEmpty(result)) {
            log.info("缓存不存在！");
            List<MenuData> menuList;
            if (userId == 1L) {
                // 管理员
                log.info("是超级管理员");
                menuList = sysMenuMapper.getMenuByUserId(null);
            } else {
                menuList = sysMenuMapper.getMenuByUserId(userId);
            }
            result = (List<MenuData>) ObjectUtil.buildDataWithChildren(menuList, null);
            pushList(String.format(Constant.RedisKey.USER_MENU, userId), result);
        }
        return result;
    }

    @Override
    public String generateToken(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            throw new UtilException("userId不能为空");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", userId);
        String token = JWTUtil.produceJwtToken(map);
        redisTemplate.opsForValue().set(String.format(Constant.RedisKey.USER_TOKEN, userId), token);
        // 过期时间 一个月
        redisTemplate.expire(String.format(Constant.RedisKey.USER_TOKEN, userId), Constant.Jwt.EXPIRE_AT, TimeUnit.SECONDS);
        return token;
    }

    @Override
    public void remove(String key) {
        if (ObjectUtil.isEmpty(key)) {
            log.warn("要删除的redis key为空");
            return;
        }
        redisTemplate.delete(key);
    }

    @Override
    public void removeByPre(String preKey) {
        if (ObjectUtil.isEmpty(preKey)) {
            log.warn("要删除的redis key为空");
            return;
        }
        Set keys = redisTemplate.keys(String.format(preKey, "*"));
        redisTemplate.delete(keys);
    }

    /**
     * Redis List 保存方式
     *
     * @param key
     * @param value
     */
    private <T> void pushList(String key, T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * Redis List 批量保存方式
     *
     * @param key
     * @param values
     */
    private <T> void pushList(String key, List<T> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * Redis 获取list中所有数据
     *
     * @param key
     * @return
     */
    private <T> List<T> getRedisListsAll(String key) {
        return getRedisListsAll(key, DEFAULT_LIST_START, DEFAULT_LIST_END);
    }

    /**
     * Redis 获取list中指定范围数据
     *
     * @param key
     * @param start 开始值
     * @param end   结束值
     * @return
     */
    private <T> List<T> getRedisListsAll(String key, int start, int end) {
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }
}
