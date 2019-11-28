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
 * @author Jin 2019-11-28
 */
@Data
@Entity
@Table(name = "bs_user_info")
public class BsUserInfo {

	/**
	 * 系统中的user_id指的此id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * basic_user_info表的id
	 */
	private Long basicUserId;

	private String loginName;

	/**
	 * 类型
	 */
	private Long type;

	/**
	 * 状态 0禁用  1启用
	 */
	private Long status;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
