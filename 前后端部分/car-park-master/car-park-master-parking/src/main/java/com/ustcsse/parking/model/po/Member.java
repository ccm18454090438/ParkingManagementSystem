package com.ustcsse.parking.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author ustcsse
 */
@Data
@TableName("member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 停车场卡信息表
     */
    @ApiModelProperty("会员id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty("会员姓名")
    private String name;

    /**
     * 卡类型(1是季卡，2是月卡，3是年卡）
     */
    @ApiModelProperty("会员卡类型[1:季卡，2:月卡，3:年卡]")
    private Integer type;

    /**
     * 剩余天数
     */
    @ApiModelProperty("剩余有效天数")
    private Integer dayleft;

    /**
     * 创建日期
     */
    @ApiModelProperty("会员创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateSerializer.class)//localdate转json字符串日期
    private LocalDate createtime;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carplate;

    /**
     * 当前日期-基准日期=已用天数（<= 剩余天数为可用）
     */
    @ApiModelProperty("会员基准日：当前日期-基准日期=已用天数（<= 剩余天数为可用）")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate basedate;

    /**
     * 停车场id
     */
    @ApiModelProperty("所在停车场")
    private Integer parkinglotid;

    /**
     * 停车场名称
     */
    @ApiModelProperty("所在停车场名称")
    private String parkinglotName;

    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String phone;


}
