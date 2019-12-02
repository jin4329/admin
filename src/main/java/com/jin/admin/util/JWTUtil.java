package com.jin.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jin.admin.common.constant.Constant;
import com.jin.admin.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTUtil {
    /**
     * 使用传入的值获得JWT token,通常情况是登录的时候调用。
     *
     * @param map 例如 传入的map.put("userId",4481973312226302L)
     * @return 获得的token
     */
    public static String produceJwtToken(Map<String, Object> map) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(Constant.Jwt.SECRET);
            JWTCreator.Builder builder = JWT.create().withIssuer(Constant.Jwt.ISSUER);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object val = entry.getValue();
                if (val instanceof Long) {
                    builder.withClaim(entry.getKey(), (Long) val);
                } else if (val instanceof Integer) {
                    builder.withClaim(entry.getKey(), (Integer) val);
                } else if (val instanceof Boolean) {
                    builder.withClaim(entry.getKey(), (Boolean) val);
                } else if (val instanceof Double) {
                    builder.withClaim(entry.getKey(), (Double) val);
                } else if (val instanceof String) {
                    builder.withClaim(entry.getKey(), (String) val);
                } else {
                    builder.withClaim(entry.getKey(), val.toString());
                }
            }
            builder.withExpiresAt(buildExpirationDate());

            return builder.sign(algorithm);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException("系统异常");
        }
    }

    private static Date buildExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Constant.Jwt.EXPIRE_AT);
        return calendar.getTime();
    }

    /**
     * 获取用户ID
     *
     * @param token JWT token
     * @return
     */
    public static Long getUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constant.Jwt.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(Constant.Jwt.ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asLong();
        } catch (Exception e) {
            log.error("没有token或解析错误：{}", token);
        }

        return null;
    }

    public static Long getUserId() {
        return getUserId(getToken());
    }

    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getToken(request);
    }

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constant.Jwt.NAME);
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter(Constant.Jwt.NAME);
        }
        return token;
    }
}
