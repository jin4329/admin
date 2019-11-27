package com.jin.admin.common.aop;

import com.jin.admin.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @Pointcut("execution(public * com.jin.admin.controller..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        log.info("请求开始, url: {}, method: {}, params: {}", url, method, pjp.getArgs());

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        Object data = ReflectUtils.invokeGetter(result, "data");
        Integer size = null;

        if (data instanceof List) {
            try {
                List list = (List) data;
                if (list.size() > 100) {
                    size = list.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (data instanceof Set) {
            try {
                Set set = (Set) data;
                if (set.size() > 100) {
                    size = set.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long time = System.currentTimeMillis() - start;

        log.info("调用结束，用时：{}，出参：{}", time, ObjectUtils.isEmpty(size) ? result : "是集合，长度为" + size);
        return result;

    }
}
