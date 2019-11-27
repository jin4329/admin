package com.jin.admin.common;

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

}
