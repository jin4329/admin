package com.jin.admin.model;

import java.util.Date;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import org.hibernate.annotations.*;

/**
 * 
 * @author Jin 2019-11-29
 */
@Data
@Entity
@Table(name = "sys_menu")
public class SysMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Integer hidden;

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

	@CreationTimestamp
	private Date createTime;

	/**
	 * 创建者
	 */
	private Long createBy;

}
