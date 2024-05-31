package com.ustcsse.parking.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ustcsse
 */
@Data
@TableName("parking")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("停车记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车位号，对应parkinglots的parkinglotid
     */
    @ApiModelProperty("停车场id")
    private Integer parkinglotid;

    /**
     * 停车场名
     */
    @ApiModelProperty("停车场名")
    private String parkinglotName;

    /**
     * 会员id
     */
    @ApiModelProperty("会员id")
    private int memberid;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carplate;

    /**
     * 入场时间
     */
    @ApiModelProperty("入场时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateTimeSerializer.class)//localdate转json字符串日期
    private LocalDateTime intime;

    /**
     * 出场时间
     */
    @ApiModelProperty("出场时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateTimeSerializer.class)//localdate转json字符串日期
    private LocalDateTime outtime;

    /**
     * 二次确认字段
     */
    @ApiModelProperty("二次确认字段")
    private int status;


}
