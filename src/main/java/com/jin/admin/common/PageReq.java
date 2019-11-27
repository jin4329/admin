package com.jin.admin.common;

import lombok.Data;

/**
 * @author Jin
 * @description
 * @date 2019/7/15 11:59
 */
@Data
public class PageReq {

    private Integer page = 1;
    private Integer pageSize = 20;

}
