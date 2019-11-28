package com.jin.admin.response;

import lombok.Data;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 18:27
 */
@Data
public class UserInfoData {
    /**
     * 基础信息id
     */
    private Long basicUserId;

    /**
     * 本账号userId
     */
    private Long userId;

    private String markId;

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

    private Long sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 人状态 0禁用  1启用
     */
    private Long basicUserStatus;

    private String loginName;

    /**
     * 类型
     */
    private Long type;

    /**
     * 本账号状态 0禁用  1启用
     */
    private Long status;

}
