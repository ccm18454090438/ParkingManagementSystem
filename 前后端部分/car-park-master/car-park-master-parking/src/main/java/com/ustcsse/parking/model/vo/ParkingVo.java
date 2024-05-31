package com.ustcsse.parking.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ParkingVo {
    @ApiModelProperty("车牌号")
    private String carplate;

    @ApiModelProperty("入场时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateTimeSerializer.class)//localdate转json字符串日期
    private LocalDateTime inTime;

    @ApiModelProperty("出场时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateTimeSerializer.class)//localdate转json字符串日期
    private LocalDateTime outTime;

    @ApiModelProperty("停车场id")
    private int parkinglotid;

    @ApiModelProperty("停车场名称")
    private String parkinglotName;
}
