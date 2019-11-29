package com.jin.admin.controller.admin;

import com.jin.admin.common.annotation.NoneToken;
import com.jin.admin.common.basebean.AbstractController;
import com.jin.admin.common.basebean.BaseRet;
import com.jin.admin.common.basebean.PageRes;
import com.jin.admin.param.GetDictPageParam;
import com.jin.admin.param.SaveDictParam;
import com.jin.admin.response.DictData;
import com.jin.admin.service.admin.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jin
 * @description
 * @date 2019/11/26 14:39
 */
@Slf4j
@RequestMapping("/admin/dict/")
@RestController
public class DictController extends AbstractController {
    @Autowired
    private DictService dictService;

    @NoneToken
    @PostMapping(path = "page")
    public BaseRet<PageRes<DictData>> getDictPage(@RequestBody GetDictPageParam param) {
        log.info("111111111111111111");
        return BaseRet.ok(dictService.getDictPage(param));
    }
    @PostMapping(path = "save")
    public BaseRet save(@RequestBody @Valid SaveDictParam param) {
        dictService.save(param);
        return BaseRet.ok();
    }
    @DeleteMapping(path = "remove")
    public BaseRet remove(Long id) {
        dictService.remove(id);
        return BaseRet.ok();
    }
}
