package com.jin.admin.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jin.admin.common.PageRes;
import com.jin.admin.dao.mapper.SysDictMapper;
import com.jin.admin.dao.repository.SysDictRepository;
import com.jin.admin.model.SysDict;
import com.jin.admin.param.GetDictPageParam;
import com.jin.admin.param.SaveDictParam;
import com.jin.admin.response.DictData;
import com.jin.admin.service.admin.DictService;
import com.jin.admin.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 13:58
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private SysDictRepository sysDictRepository;
    @Autowired
    private SysDictMapper sysDictMapper;
    @Override
    public PageRes<DictData> getDictPage(GetDictPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<DictData> dictList = sysDictMapper.getDictList(param);
        // 满足要求的父级
        PageRes<DictData> result = new PageRes<>(new PageInfo<>(dictList));

        // 所有的子级
        GetDictPageParam dictPageParam = new GetDictPageParam();
        dictPageParam.setPid(0);
        List<DictData> children = sysDictMapper.getDictList(dictPageParam);
        result.getData().addAll(children);
        // 组装数据
        List<DictData> dictData = (List<DictData>) ObjectUtil.buildDataWithChildren(result.getData(), null, "pid", "children");
        result.setData(dictData);
        return result;
    }

    @Override
    @Transactional
    public void save(SaveDictParam param) {
        SysDict dict;
        if (ObjectUtil.isNotEmpty(param.getId())) {
            dict = sysDictRepository.getOne(param.getId());
        } else {
            dict = new SysDict();
        }
        BeanUtils.copyProperties(param, dict);
        sysDictRepository.save(dict);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        sysDictRepository.deleteById(id);
    }
}
