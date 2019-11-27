package com.jin.admin.common.basebean;

import com.jin.admin.common.exception.BusinessException;
import lombok.Data;

/**
 * @author Jin
 * @description controller统一返回包装类
 * @date 2019/11/26 9:51
 */
@Data
public class BaseRet<T> {

    private T data;

    private String code = "0";

    private String msg = "ok";

    private static final String ERROR_CODE = "-1";

    private static final String ERROR_MSG = "error";

    private BaseRet() {
    }

    public static BaseRet newInstance() {
        return new BaseRet();
    }

    private BaseRet(T data) {
        this.data = data;
    }

    private BaseRet(String msg) {
        this.code = msg;
        this.msg = ERROR_CODE;
    }

    private BaseRet(Exception e) {
        this.code = ERROR_CODE;
        this.msg = e.getMessage();
    }

    private BaseRet(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> BaseRet<T> ok() {
        return new BaseRet<>();
    }

    public static <T> BaseRet<T> ok(T data) {
        return new BaseRet<>(data);
    }

    public static BaseRet error(BusinessException e) {
        return new BaseRet<>(e.getCode(), e.getMsg());
    }

    public static <T> BaseRet<T> error(String msg) {
        return new BaseRet<>(msg);
    }

    public static BaseRet error(Exception e) {
        return new BaseRet<>(e);
    }

    public static BaseRet error() {
        return new BaseRet(ERROR_CODE, ERROR_MSG);
    }
}
