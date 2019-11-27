package com.jin.admin.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jin
 * @description
 * @date 2019/7/15 15:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UtilException extends RuntimeException {
    private String code;
    private String msg;

    public UtilException() {
    }

    public UtilException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public UtilException(String message) {
        this.code = "-1";
        this.msg = message;
    }

}
