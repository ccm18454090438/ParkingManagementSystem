package com.ustcsse.order.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDto {
    @ApiModelProperty("收款方式[0现金，1支付宝，2微信，9从卡中扣费]")
    private String method;

    @ApiModelProperty("车牌号")
    private String carplate;

    @ApiModelProperty("停车场")
    private String parkinglotid;

    @ApiModelProperty("收款类型[临停：0，充值：1月卡，2季卡，3年卡]")
    private String type;
}
