package com.jin.admin.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.jin.admin.dao.mapper.SysLogMapper;
import com.jin.admin.model.SysLog;
import com.jin.admin.util.IpUtil;
import com.jin.admin.util.JWTUtil;
import com.jin.admin.util.ReflectUtils;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Jin
 * @description 接口调用日志
 * @date 2019/10/11 10:48
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Pointcut("execution(public * com.jin.admin.controller..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        SysLog sysLog = new SysLog();

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        sysLog.setIp(IpUtil.getIpAddress(request));
        sysLog.setUserId(JWTUtil.getUserId());
        sysLog.setMethod(method);
        Object[] args = pjp.getArgs();
        String param = null;
        try {
            param = JSONObject.toJSONString(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysLog.setParams(param);
        sysLog.setUrl(url);

        log.info("请求开始, url: {}, method: {}, params: {}", url, method, param);

        Date start = new Date();
        Object result = pjp.proceed();
        Object data = ReflectUtils.invokeGetter(result, "data");
        Integer size = null;

        if (data instanceof List) {
            try {
                List list = (List) data;
                size = list.size();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (data instanceof Set) {
            try {
                Set set = (Set) data;
                size = set.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Date endTime = new Date();
        long time = endTime.getTime() - start.getTime();

        sysLog.setStartTime(start);
        sysLog.setEndTime(endTime);
        sysLog.setTime(time);
        try {
            String dataStr = JSONObject.toJSONString(data);
            if (dataStr.length() < 1000) {
                size = null;
            }
            sysLog.setResult(JSONObject.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysLog.setCode(ReflectUtils.invokeGetter(result, "code"));
        sysLog.setMsg(ReflectUtils.invokeGetter(result, "msg"));
        sysLogMapper.insert(sysLog);

        log.info("调用结束，用时：{}，出参：{}", time, ObjectUtils.isEmpty(size) ? result : "是集合，长度为" + size);
        return result;

    }
}
