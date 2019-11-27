package com.jin.admin.controller.admin;

import com.jin.admin.common.basebean.BaseRet;
import com.jin.admin.common.basebean.PageRes;
import com.jin.admin.param.GetDictPageParam;
import com.jin.admin.param.SaveDictParam;
import com.jin.admin.response.DictData;
import com.jin.admin.service.admin.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Jin
 * @description
 * @date 2019/11/26 14:39
 */
@RequestMapping("/dict/")
@RestController
public class DictController {
    @Autowired
    private DictService dictService;

    @RequestMapping("page")
    public BaseRet<PageRes<DictData>> getDictPage(@RequestBody GetDictPageParam param) {
        return BaseRet.ok(dictService.getDictPage(param));
    }
    @RequestMapping("save")
    public BaseRet save(@RequestBody @Valid SaveDictParam param) {
        dictService.save(param);
        return BaseRet.ok();
    }
    @RequestMapping("remove")
    public BaseRet remove(Long id) {
        dictService.remove(id);
        return BaseRet.ok();
    }
}
