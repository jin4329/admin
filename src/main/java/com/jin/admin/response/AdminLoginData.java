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
    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String headImgUrl;


    private String loginName;
    private List<MenuData> menuList;
    private String token;
}
