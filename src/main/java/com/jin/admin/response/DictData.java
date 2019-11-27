package com.jin.admin.response;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 13:53
 */
@Data
public class DictData {
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

    private Date updateTime;

    private Date createTime;

    private List<DictData> children;
}
