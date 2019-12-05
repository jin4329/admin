package com.jin.admin.service.admin.impl;

import com.jin.admin.common.constant.Constant;
import com.jin.admin.common.constant.TypeEnum;
import com.jin.admin.common.exception.BusinessException;
import com.jin.admin.dao.repository.BsUserInfoRepository;
import com.jin.admin.dao.repository.SysAdminUserRepository;
import com.jin.admin.model.BsUserInfo;
import com.jin.admin.model.SysAdminUser;
import com.jin.admin.param.AdminLoginParam;
import com.jin.admin.response.AdminLoginData;
import com.jin.admin.response.UserInfoData;
import com.jin.admin.service.admin.AdminUserService;
import com.jin.admin.service.admin.RedisService;
import com.jin.admin.util.EnumUtil;
import com.jin.admin.util.MD5Util;
import com.jin.admin.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:11
 */
@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private BsUserInfoRepository bsUserInfoRepository;
    @Autowired
    private SysAdminUserRepository sysAdminUserRepository;

    @Override
    public AdminLoginData login(AdminLoginParam param) {
        // 获取账号基本信息
        UserInfoData userInfo = getUserInfoData(param.getUsername(), param.getPassword());
        // 检查账号状态
        checkUserStatus(userInfo.getBasicUserStatus(), userInfo.getStatus());
        AdminLoginData result = new AdminLoginData();
        BeanUtils.copyProperties(userInfo, result);

        // 设置权限
        result.setMenuList(redisService.getMenuList(userInfo.getUserId()));
        // 设置token
        result.setToken(redisService.generateToken(userInfo.getUserId()));
        return result;
    }

    @Override
    public void loginOut(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            throw new BusinessException("userId不能为空");
        }
        redisService.remove(String.format(Constant.RedisKey.USER_TOKEN, userId));
    }

    private UserInfoData getUserInfoData(String loginName, String password) {
        BsUserInfo bsUserInfo = bsUserInfoRepository.getByLoginNameAndType(loginName, TypeEnum.UserType.ADMIN.getCode());
        if (ObjectUtil.isEmpty(bsUserInfo)) {
            throw new BusinessException("用户不存在");
        }
        SysAdminUser adminUser = sysAdminUserRepository.getByUserId(bsUserInfo.getId());
        boolean verify = MD5Util.verify(password, adminUser.getPassword());
        if (!verify) {
            throw new BusinessException("密码不正确");
        }
        UserInfoData userInfo = redisService.getBasicUserInfo(bsUserInfo.getId());
        if (ObjectUtil.isEmpty(userInfo)) {
            throw new BusinessException("用户不存在");
        }
        return userInfo;
    }

    /**
     * @param basicUserStatus 人的状态
     * @param status          此账号的状态
     * @return void
     * @author Jin
     * @description
     */
    private void checkUserStatus(Long basicUserStatus, Long status) {
        if (!TypeEnum.UserStatus.ENABLE.getCode().equals(basicUserStatus)) {
            throw new BusinessException("账号（状态：" + EnumUtil.getEnumMsg(TypeEnum.UserStatus.class, basicUserStatus) + "）异常");
        }
        if (!TypeEnum.UserStatus.ENABLE.getCode().equals(status)) {
            throw new BusinessException("账号（状态：" + EnumUtil.getEnumMsg(TypeEnum.UserStatus.class, basicUserStatus) + "）异常");
        }
    }

}
