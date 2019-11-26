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
 * @author Jin 2019-11-26
 */
@Data
@Entity
@Table(name = "sys_menu")
public class SysMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单路由
	 */
	private String uri;

	/**
	 * 父id
	 */
	private Long pid;

	/**
	 * 0禁用  1启用
	 */
	private Long status;

	@CreationTimestamp
	private Date createTime;

}
