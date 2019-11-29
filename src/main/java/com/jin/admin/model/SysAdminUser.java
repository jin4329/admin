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
@Table(name = "sys_admin_user")
public class SysAdminUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * bs_user_info的id
	 */
	private Long userId;

	/**
	 * 密码
	 */
	private String password;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
