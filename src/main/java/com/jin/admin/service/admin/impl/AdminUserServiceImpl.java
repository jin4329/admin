package com.jin.admin.service.admin.impl;

import com.jin.admin.common.constant.TypeEnum;
import com.jin.admin.common.exception.BusinessException;
import com.jin.admin.dao.mapper.SysAdminUserMapper;
import com.jin.admin.dao.repository.BsUserInfoRepository;
import com.jin.admin.dao.repository.SysAdminUserRepository;
import com.jin.admin.model.BsUserInfo;
import com.jin.admin.param.AdminLoginParam;
import com.jin.admin.response.AdminLoginData;
import com.jin.admin.response.UserInfoData;
import com.jin.admin.service.admin.AdminUserService;
import com.jin.admin.service.admin.RedisService;
import com.jin.admin.util.EnumUtil;
import com.jin.admin.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public AdminLoginData login(AdminLoginParam param) {
        // 获取账号基本信息
        UserInfoData userInfo = getUserInfoData(param.getLoginName());
        // 检查账号状态
        checkUserStatus(userInfo.getBasicUserStatus(), userInfo.getStatus());
        AdminLoginData result = new AdminLoginData();
        result.setUserInfo(userInfo);

        // 设置权限


        return null;
    }

    private UserInfoData getUserInfoData(String loginName) {
        BsUserInfo bsUserInfo = bsUserInfoRepository.getByLoginNameAndType(loginName, TypeEnum.UserType.ADMIN.getCode());
        if (ObjectUtil.isEmpty(bsUserInfo)) {
            throw new BusinessException("用户不存在");
        }
        UserInfoData userInfo = redisService.getBasicUserInfo(bsUserInfo.getId());
        if (ObjectUtil.isEmpty(userInfo)) {
            throw new BusinessException("用户不存在");
        }
        return userInfo;
    }

    /**
     * @author Jin
     * @description
     * @param basicUserStatus 人的状态
     * @param status 此账号的状态
     * @return void
     */
    private void checkUserStatus(Long basicUserStatus, Long status) {
        if (!TypeEnum.UserStatus.ENABLE.getCode().equals(basicUserStatus)) {
            throw new BusinessException("账号（状态：" + EnumUtil.getEnumMsg(TypeEnum.UserStatus.class, basicUserStatus) +"）异常");
        }
        if (!TypeEnum.UserStatus.ENABLE.getCode().equals(status)) {
            throw new BusinessException("账号（状态：" + EnumUtil.getEnumMsg(TypeEnum.UserStatus.class, basicUserStatus) +"）异常");
        }
    }

    void setPermission(AdminLoginData data) {

    }


}
