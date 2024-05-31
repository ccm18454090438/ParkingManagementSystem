package com.ustcsse.parking.model.po;

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
@TableName("parking")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车位号，对应parkspace的parkid
     */
    private Integer parkinglotid;

    /**
     * 会员id
     */
    private String memberid;

    /**
     * 车牌号
     */
    private String carplate;

    /**
     * 入场时间
     */
    private LocalDateTime intime;

    /**
     * 出场时间
     */
    private LocalDateTime outtime;


}
