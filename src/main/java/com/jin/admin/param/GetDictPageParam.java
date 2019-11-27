package com.jin.admin.param;

import com.jin.admin.common.basebean.PageReq;
import lombok.Data;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 13:55
 */
@Data
public class GetDictPageParam extends PageReq {
    private String name;
    private Integer pid;
    /**
     * 0禁用  1启用
     */
    private Long status;
}
