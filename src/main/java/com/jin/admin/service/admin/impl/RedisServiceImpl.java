package com.jin.admin.service.admin.impl;

import com.jin.admin.common.constant.Constant;
import com.jin.admin.common.exception.BusinessException;
import com.jin.admin.dao.mapper.BsBasicUserInfoMapper;
import com.jin.admin.response.UserInfoData;
import com.jin.admin.service.admin.RedisService;
import com.jin.admin.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 18:26
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BsBasicUserInfoMapper bsBasicUserInfoMapper;

    @Override
    public UserInfoData getBasicUserInfo(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        UserInfoData userInfo = (UserInfoData) redisTemplate.opsForValue().get(String.format(Constant.RedisKey.USER_INFO, userId));
        if (ObjectUtil.isEmpty(userInfo)) {
            // 从数据库拿
            userInfo = bsBasicUserInfoMapper.getUserInfoData(userId);
            if (ObjectUtil.isEmpty(userInfo)) {
                throw new BusinessException("数据不存在！");
            }
            redisTemplate.opsForValue().set(String.format(Constant.RedisKey.USER_INFO, userId), userInfo);
        }
        return userInfo;
    }

    @Override
    public String getToken(Long userId) {
        return (String) redisTemplate.opsForValue().get(String.format(Constant.RedisKey.USER_TOKEN, userId));
    }
}
