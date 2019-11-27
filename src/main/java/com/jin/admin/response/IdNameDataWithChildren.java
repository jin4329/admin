package com.jin.admin.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Date 2019/6/26 18:02
 * @Created Jin
 */
@Data
@NoArgsConstructor
public class IdNameDataWithChildren {
    private Integer id;
    private Integer parentId;
    private List<IdNameDataWithChildren> children;

    public IdNameDataWithChildren(Integer id, Integer parentId) {
        this.id = id;
        this.parentId = parentId;
    }
}
