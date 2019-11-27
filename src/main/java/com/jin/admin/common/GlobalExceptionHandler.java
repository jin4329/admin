package com.jin.admin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Jin
 * @description 全局异常处理
 * @date 2019/7/23 10:02
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理@Valid校验异常
     * @param exception MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRet validationException(MethodArgumentNotValidException exception){
        BaseRet  ret = BaseRet.newInstance();
        ret.setCode("-1");
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            for (ObjectError x : result.getAllErrors()) {
                FieldError fieldError = (FieldError) x;
                ret.setMsg(fieldError.getField() + fieldError.getDefaultMessage());
                log.error("请求参数错误：object:{}， field:{}，message:{}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                break;
            }
        }
        return ret;
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public BaseRet parameterTypeException(HttpMessageConversionException exception){
        BaseRet result = BaseRet.newInstance();
        result.setCode("-1");
        result.setMsg("类型转换错误:" + exception.getCause().getLocalizedMessage());
        log.error("类型转换错误:{}", exception.getCause().getLocalizedMessage());
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    public BaseRet businessException(BusinessException exception){
        log.error("异常信息：{}",exception.getMsg());
        return BaseRet.error(exception);
    }

    @ExceptionHandler(Exception.class)
    public BaseRet businessException(Exception exception){
        log.error("异常信息:"+ exception);
        return BaseRet.error(exception);
    }

}
