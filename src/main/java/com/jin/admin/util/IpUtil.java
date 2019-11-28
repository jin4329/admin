package com.jin.admin.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * @author Jin
 * @description
 * @date 2019/11/28 15:52
 */
public class IpUtil {
    /**
     * 默认IP地址
     */
    public final static String ERROR_IP = "127.0.0.1";

    /**
     * IP地址正则表达式
     */
    public final static Pattern PATTERN = Pattern.compile(
            "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    /**
     * @author Jin
     * @description 根据请求验证IP地址
     * @param request request
     * @return boolean
     */
    public static boolean isValidIp(HttpServletRequest request) {

        // 获取用户真实的IP地址
        String ip = getUserIp(request);
        // 验证IP地址是否符合规则
        return isValidIp(ip);
    }

    /**
     * @author Jin
     * @description 获取远程IP地址
     * @param request
     * @return java.lang.String
     */
    public static String getRemoteIp(HttpServletRequest request) {

        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        // 过滤反向代理的ip
        String[] stemps = ip.split(",");
        if (ObjectUtil.isNotEmpty(stemps)) {
            // 得到第一个IP，即客户端真实IP
            ip = stemps[0];
        }

        ip = ip.trim();
        if (ip.length() > 23) {
            ip = ip.substring(0, 23);
        }

        return ip;
    }

    /**
     * @author Jin
     * @description 获取用户真实的IP地址
     * @param request
     * @return java.lang.String
     */
    public static String getUserIp(HttpServletRequest request) {

        // 优先取 X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        if (isNotIp(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (isNotIp(ip)) {
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = ERROR_IP;
            }
        }
        if ("unknown".equalsIgnoreCase(ip)) {
            ip = ERROR_IP;
            return ip;
        }
        int index = ip.indexOf(',');
        if (index >= 0) {
            ip = ip.substring(0, index);
        }

        return ip;
    }

    /**
     * @author Jin
     * @description 获取末尾IP地址段
     * @param request
     * @return java.lang.String
     */
    public static String getLastIpSegment(HttpServletRequest request) {

        // 获取用户真实的IP地址
        String ip = getUserIp(request);
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * @author Jin
     * @description 是正确的ip
     * @param ip
     * @return boolean
     */
    public static boolean isValidIp(String ip) {

        if (ObjectUtil.isEmpty(ip)) {
            return false;
        }
        return PATTERN.matcher(ip).matches();
    }

    /**
     * @author Jin
     * @description 获取服务器的会后一个地址段
     * @param
     * @return java.lang.String
     */
    public static String getLastServerIpSegment() {

        String ip = getServerIp();
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * @author Jin
     * @description 获取服务器IP地址
     * @param
     * @return java.lang.String
     */
    public static String getServerIp() {

        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ERROR_IP;
    }

    /**
     * @author Jin
     * @description 获取IP地址
     * IP地址经过多次反向代理后会有多个IP值，其中第一个IP才是真实IP，所以不能通过request.getRemoteAddr()获取IP地址，
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP才是用户真实IP地址。
     * @param request
     * @return java.lang.String
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ip = null;
        try {
            // 获取多次代理后的IP字符串值
            String xFor = request.getHeader("X-Forwarded-For");
            if (!isNotIp(xFor)) {
                // 多次反向代理后会有多个IP值，第一个用户真实的IP地址
                int index = xFor.indexOf(",");
                if (index >= 0) {
                    return xFor.substring(0, index);
                } else {
                    return xFor;
                }
            }
            // nginx
            ip = request.getHeader("X-Real-IP");
            if (isNotIp(ip)) {
                // apache
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (isNotIp(ip)) {
                // weblogic
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (isNotIp(ip)) {
                // http 客户端IP
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (isNotIp(ip)) {
                // http 代理服务器IP
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (isNotIp(ip)) {
                // 远程ip
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    private static boolean isNotIp(String ip) {
        return ObjectUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
