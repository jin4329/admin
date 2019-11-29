package com.jin.admin.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:03
 */
@Data
public class AdminLoginParam {
    @NotEmpty
    private String loginName;
    @NotEmpty
    private String password;
}
