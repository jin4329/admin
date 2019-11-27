package com.jin.admin.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 13:58
 */
@Data
public class SaveDictParam {
    private Long id;

    /**
     * 父id
     */
    private Long pid;

    @NotNull
    private String name;

    private String remark;

    /**
     * 0禁用  1启用
     */
    @NotNull
    private Long status;

}
