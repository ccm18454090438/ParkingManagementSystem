package com.ustcsse.parking.model;

import com.ustcsse.common.model.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParkingQueryParams {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("停车场id")
    private int parkinglotid;

    @ApiModelProperty("停车场名")
    private String parkinglotName;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carplate;

    @ApiModelProperty("入库时间范围查询")
    private String[] dateRange;

    @ApiModelProperty("出库时间范围查询")
    private String[] dateRange2;

    @ApiModelProperty("分页查询参数")
    private PageParams pageParams;
}
