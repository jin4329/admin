package com.jin.admin.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:03
 */
@Data
public class AdminLoginParam {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
