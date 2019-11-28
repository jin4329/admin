package com.jin.admin.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import org.hibernate.annotations.*;

/**
 * 
 * @author Jin 2019-11-28
 */
@Data
@Entity
@Table(name = "sys_permission")
public class SysPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * 权限类型 1菜单  2文件
	 */
	private Long type;

}
