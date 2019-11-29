package com.jin.admin.service.admin;

import com.jin.admin.param.AdminLoginParam;
import com.jin.admin.response.AdminLoginData;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author Jin
 * @description
 * @date 2019/11/29 16:10
 */
public interface AdminUserService {
    AdminLoginData login(AdminLoginParam param);
}
