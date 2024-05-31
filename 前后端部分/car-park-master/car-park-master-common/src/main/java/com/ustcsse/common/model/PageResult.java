package com.ustcsse.common.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 分页查询结果模型类
 * @date 2023/2/11 15:40
 */
@Data
@ToString
public class PageResult<T> implements Serializable {

    // 数据列表
    private List<T> items;

    //总记录数
    private int counts;

    //当前页码
    private int page;

    //每页记录数
    private int pageSize;

    public PageResult(List<T> items, int counts, int page, int pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }


}
