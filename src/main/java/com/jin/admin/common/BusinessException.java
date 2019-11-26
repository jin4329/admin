package com.jin.admin.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jin
 * @description
 * @date 2019/7/15 15:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private String code;
    private String msg;
    public BusinessException() {
    }

    public BusinessException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String message) {
        this.code = "-1";
        this.msg = message;
    }

}
