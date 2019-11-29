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
	 * 昵称
	 */
	private String nickname;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * 邮箱
	 */
	private String email;

	private Long sex;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 头像
	 */
	private String headImgUrl;

	/**
	 * 状态 0禁用  1启用
	 */
	private Long status;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
