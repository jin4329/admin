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
@Table(name = "sys_dict")
public class SysDict {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 父id
	 */
	private Long pid;

	private String name;

	private String remark;

	/**
	 * 0禁用  1启用
	 */
	private Long status;

	@UpdateTimestamp
	private Date updateTime;

	@CreationTimestamp
	private Date createTime;

}
