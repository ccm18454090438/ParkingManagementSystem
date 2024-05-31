package com.ustcsse.order.model;

import com.ustcsse.common.model.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderRequestParams {
    @ApiModelProperty("分页参数")
    private PageParams pageParams;

    @ApiModelProperty("收款方式[0现金，1支付宝，2微信，9从卡中扣费]")
    private String method;

    @ApiModelProperty("车牌号")
    private String carplate;

    @ApiModelProperty("停车场")
    private int parkinglotid;

    @ApiModelProperty("停车场名称")
    private String parkinglotName;

    @ApiModelProperty("收款类型[临停：0，充值：1月卡，2季卡，3年卡]")
    private String type;

    @ApiModelProperty("订单创建日期")
    private String[] dateRange;
}
