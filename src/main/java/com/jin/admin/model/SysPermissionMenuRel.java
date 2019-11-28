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
@Table(name = "sys_permission_menu_rel")
public class SysPermissionMenuRel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 权限id
	 */
	private Long permissionId;

	/**
	 * 菜单id
	 */
	private Long menuId;

}
