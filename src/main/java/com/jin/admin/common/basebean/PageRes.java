package com.jin.admin.common.basebean;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jin
 * @description
 * @date 2019/7/15 13:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRes<T> {
    
    private List<T> data;
    private Long total;

    public PageRes(PageInfo info) {
        this.data = info.getList();
        this.total = info.getTotal();
    }
}
