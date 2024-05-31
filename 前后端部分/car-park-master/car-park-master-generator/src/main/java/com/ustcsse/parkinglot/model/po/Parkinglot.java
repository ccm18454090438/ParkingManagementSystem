package com.ustcsse.parkinglot.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("parkinglot")
public class Parkinglot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车位总数
     */
    private Integer count;

    /**
     * 已停车数量
     */
    private Integer caramount;

    /**
     * 剩余车位数
     */
    private Integer left;

    /**
     * 停车场名
     */
    private String name;

    /**
     * 管理员
     */
    private String administrator;

    /**
     * 管理员联系方式
     */
    private String phone;

    /**
     * 停车场位置
     */
    private String address;


}
