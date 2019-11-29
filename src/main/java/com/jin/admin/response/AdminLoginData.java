package com.jin.admin.response;

import lombok.Data;

import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:09
 */
@Data
public class AdminLoginData {
    private UserInfoData userInfo;
    private List<MenuData> menuList;
}
