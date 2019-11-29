package com.jin.admin.controller.admin;

import com.jin.admin.common.basebean.AbstractController;
import com.jin.admin.common.basebean.BaseRet;
import com.jin.admin.param.AdminLoginParam;
import com.jin.admin.response.AdminLoginData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Jin
 * @description
 * @date 2019/11/26 9:51
 */
@RequestMapping("/admin/user/")
@RestController
public class AdminUserController extends AbstractController {
    @PostMapping("/login")
    public BaseRet<AdminLoginData> login(@RequestBody @Valid AdminLoginParam param) {

        return BaseRet.ok();
    }
}
