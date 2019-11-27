package com.jin.admin.service.admin;

import com.jin.admin.common.basebean.PageRes;
import com.jin.admin.param.GetDictPageParam;
import com.jin.admin.param.SaveDictParam;
import com.jin.admin.response.DictData;

/**
 * @author Jin
 * @description
 * @date 2019/11/27 13:57
 */
public interface DictService {
    PageRes<DictData> getDictPage(GetDictPageParam param);

    void save(SaveDictParam param);

    void remove(Long id);
}
