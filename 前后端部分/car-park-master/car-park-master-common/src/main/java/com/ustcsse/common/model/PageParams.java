package com.ustcsse.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageParams {
    @ApiModelProperty("页码")
    private int pageNo = 1;
    //每页显示记录数
    @ApiModelProperty("每页记录数")
    private int pageSize = 20;

    public PageParams() {
    }

    public PageParams(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
