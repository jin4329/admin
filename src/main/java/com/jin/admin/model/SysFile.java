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
@Table(name = "sys_file")
public class SysFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 文件路径
	 */
	private String url;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 类型
	 */
	private Long type;

	/**
	 * 大小，kb
	 */
	private Double size;

	/**
	 * 创建者
	 */
	private Long createBy;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
