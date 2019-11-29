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
	 * 备注
	 */
	private String remark;

	/**
	 * 0禁用  1启用
	 */
	private Long status;

	@CreationTimestamp
	private Date createTime;

	/**
	 * 创建者
	 */
	private Long createBy;

}
