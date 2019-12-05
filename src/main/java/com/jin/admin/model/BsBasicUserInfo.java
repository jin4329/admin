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
 * @author Jin 2019-12-5
 */
@Data
@Entity
@Table(name = "bs_basic_user_info")
public class BsBasicUserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String markId;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 11男  12女
	 */
	private Long sex;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 状态 0禁用  1启用
	 */
	private Long status;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
