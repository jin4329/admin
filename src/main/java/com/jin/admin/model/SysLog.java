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
@Table(name = "sys_log")
public class SysLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 创建时间
	 */
	@CreationTimestamp
	private Date createTime;

	/**
	 * 访问ip
	 */
	private String ip;

	private String token;

	private Long userId;

	/**
	 * 访问方法
	 */
	private String url;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 入参
	 */
	private String params;

	/**
	 * 返回值
	 */
	private String result;

	/**
	 * 返回的code
	 */
	private String code;

	/**
	 * 返回的msg
	 */
	private String msg;

	/**
	 * 用时 ms
	 */
	private Long time;

	/**
	 * 请求时间
	 */
	private Date startTime;

	/**
	 * 调用结束时间
	 */
	private Date endTime;

}
