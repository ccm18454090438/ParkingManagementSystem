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
@TableName("cpm_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 收入金额
     */
    @ApiModelProperty("订单收入金额/元")
    private Double amount;

    /**
     * 收入方式（0现金，1支付宝，2微信，9从卡中扣费）
     */
    @ApiModelProperty("订单收入方式[0:现金，1:支付宝，2:微信，9:会员账户]")
    private Integer method;

    /**
     * 收入类型（临停：0，充值：1月卡，2季卡，3年卡）
     */
    @ApiModelProperty("订单收入类型[0:临停缴费，1:月卡充值，2:季卡充值，3:年卡充值]")
    private Integer type;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carplate;

    /**
     * 会员id
     */
    @ApiModelProperty("会员id")
    private int memberid;

    /**
     * 收入时间
     */
    @ApiModelProperty("订单创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //json字符串日期转localdate
    @JsonSerialize(using = LocalDateTimeSerializer.class)//localdate转json字符串日期
    private LocalDateTime time;

    /**
     * 停车时长/s
     */
    @ApiModelProperty("停车时长/秒")
    private Long duration;

    /**
     * 停车记录id
     */
    @ApiModelProperty("停车记录id")
    private Integer parkingid;

    /**
     * 停车场名称
     */
    @ApiModelProperty("停车场名称")
    private String parkinglotName;

    /**
     * 停车记录id
     */
    @ApiModelProperty("停车场")
    private Integer parkinglotid;

    /**
     * 是否支付完成
     */
    @ApiModelProperty("是否支付完成")
    private Integer status;

    /**
     * 支付单号
     */
    @ApiModelProperty("付款单号")
    private long tradeNo;

}
