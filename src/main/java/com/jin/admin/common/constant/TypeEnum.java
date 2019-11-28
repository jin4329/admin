package com.jin.admin.common.constant;

import lombok.Getter;

/**
 * @author Jin
 * @description
 * @date 2019/7/15 14:26
 */
public interface TypeEnum {

    @Getter
    enum TimeCode{
        TODAY(1, "今天"),
        LAST_THREE_DAY(2, "过去三天"),
        LAST_WEEK(3, "过去一周"),
        LAST_MONTH(4, "过去一月"),
        LAST_YEAR(5, "过去一年"),
        ;
        private Integer code;
        private String name;

        TimeCode(Integer code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    @Getter
    enum Status {
        ENABLE(1L, "启用"),
        DISABLE(0L, "禁用"),
        ;
        private Long code;
        private String name;

        Status(Long code, String name) {
            this.code = code;
            this.name = name;
        }
    }

}
