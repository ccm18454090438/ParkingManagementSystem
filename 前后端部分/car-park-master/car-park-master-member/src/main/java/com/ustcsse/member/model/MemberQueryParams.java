package com.ustcsse.member.model;

import com.ustcsse.common.model.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberQueryParams {
    @ApiModelProperty("分页参数")
    private PageParams pageParams;

    @ApiModelProperty("会员姓名")
    private String name;

    @ApiModelProperty("车牌号")
    private String carplate;

    @ApiModelProperty("停车场名称")
    private String parkinglotName;

    @ApiModelProperty("手机号")
    private String phone;
}
