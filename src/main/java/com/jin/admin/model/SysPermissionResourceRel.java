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
 * @author Jin 2019-11-29
 */
@Data
@Entity
@Table(name = "sys_permission_resource_rel")
public class SysPermissionResourceRel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 权限id
	 */
	private Long permissionId;

	/**
	 * 资源id
	 */
	private Long resourceId;

	/**
	 * 权限类型 9菜单  10文件
	 */
	private Long type;

}
