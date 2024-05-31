package com.ustcsse.member.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 停车场卡信息表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 卡类型(1是季卡，2是月卡，3是年卡）
     */
    private Integer type;

    /**
     * 剩余天数
     */
    private Integer dayleft;

    /**
     * 创建日期
     */
    private LocalDate createtime;

    /**
     * 车牌号
     */
    private String carplate;

    /**
     * 当前日期-基准日期=已用天数（<= 剩余天数为可用）
     */
    private LocalDate basedate;

    /**
     * 会员卡号
     */
    private String membercard;

    /**
     * 停车场id
     */
    private Integer parkinglotid;

    /**
     * 联系方式
     */
    private String phone;


}
