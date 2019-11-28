package com.jin.admin.common.interceptor;

import com.jin.admin.common.annotation.NoneToken;
import com.jin.admin.common.constant.TypeEnum;
import com.jin.admin.common.exception.BusinessException;
import com.jin.admin.response.UserInfoData;
import com.jin.admin.service.admin.RedisService;
import com.jin.admin.util.JWTUtil;
import com.jin.admin.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Jin
 * @description 校验token拦截器
 * @date 2019/11/27 18:10
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Value("${enable.token}")
    private Boolean enableToken;
    @Value("${enable.single.sign}")
    private Boolean enableSingleSign;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ObjectUtil.isEmpty(enableToken)) {
            // 默认需要校验
            enableToken = true;
        }
        if (!enableToken) {
            // 关闭token校验
            return true;
        }

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(NoneToken.class)) {
            // 不需要校验的方法
            NoneToken noneToken = method.getAnnotation(NoneToken.class);
            if (noneToken.required()) {
                return true;
            }
        }

        String token = JWTUtil.getToken(request);

        if (ObjectUtil.isEmpty(token)) {
            throw new BusinessException("没有token");
        }
        Long userId = JWTUtil.getUserId(token);
        if (ObjectUtil.isEmpty(userId)) {
            throw new BusinessException("无法获取到用户信息，请重新登录");
        }

        String redisToken = redisService.getToken(userId);
        if (ObjectUtil.isEmpty(redisToken)) {
            throw new BusinessException("登录信息已过期，请重新登录");
        }

        if (ObjectUtil.isEmpty(enableSingleSign)) {
            enableSingleSign = false;
        }
        if (enableSingleSign && !redisToken.equalsIgnoreCase(token)) {
            // 单设备登录
            throw new BusinessException("已在其他设备登录");
        }

        // 用户信息
        UserInfoData userInfo = redisService.getBasicUserInfo(userId);
        if (ObjectUtil.isEmpty(userInfo)) {
            throw new BusinessException("无法获取到用户信息，请重新登录");
        }
        if (!(TypeEnum.Status.ENABLE.getCode().equals(userInfo.getStatus()) && TypeEnum.Status.ENABLE.getCode().equals(userInfo.getBasicUserStatus()))) {
            // 账号异常
            throw new BusinessException("账号异常");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
