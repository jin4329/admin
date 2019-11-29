package com.jin.admin.response;

import lombok.Data;

import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:05
 */
@Data
public class MenuData {
    private Long id;

    /**
     * router中的path和name，驼峰并首字母大写
     */
    private String name;

    /**
     * 路由
     */
    private String path;

    private String title;

    /**
     * 是否隐藏，隐藏即为按钮
     */
    private Boolean hidden;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 2菜单 3按钮
     */
    private Long type;

    /**
     * 0禁用  1启用
     */
    private Long status;

    /**
     * 所属页面id
     */
    private Long pageId;

    private List<MenuData> children;
}
