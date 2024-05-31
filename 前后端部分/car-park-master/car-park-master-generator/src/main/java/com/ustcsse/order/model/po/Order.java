package com.ustcsse.order.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author ustcsse
 */
@Data
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 收入金额
     */
    private Double amount;

    /**
     * 收入方式（0现金，1支付宝，2微信，9从卡中扣费）
     */
    private Integer method;

    /**
     * 收入类型（临停：0，充值：1月卡，2季卡，3年卡）
     */
    private Integer type;

    /**
     * 车牌号
     */
    private String carplate;

    /**
     * 会员id
     */
    private String memberid;

    /**
     * 收入时间
     */
    private LocalDateTime time;

    /**
     * 停车时长
     */
    private Long duration;

    /**
     * 停车记录id
     */
    private Integer parkingid;


}
