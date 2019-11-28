package com.jin.admin.common.basebean;

import com.jin.admin.util.JWTUtil;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 17:26
 */
public abstract class AbstractController {
    public Long userId() {
        return JWTUtil.getUserId();
    }

}
