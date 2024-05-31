package com.ustcsse.parking.model.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ParkingDto {
    private static final long serialVersionUID = 1L;

    /**
     * 停车位号，对应parkspace的parkid
     */
    @ApiModelProperty("停车场id")
    private int parkinglotid;

    @ApiModelProperty("停车场名称")
    private String parkinglotName;


    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carplate;
}
