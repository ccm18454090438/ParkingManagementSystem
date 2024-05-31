package com.ustcsse.parkinglot.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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

    @ApiModelProperty("停车场id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车位总数
     */
    @ApiModelProperty("总车位数")
    private Integer total;

    /**
     * 已停车数量
     */
    @ApiModelProperty("已停车数量")
    private Integer carAmount;

    /**
     * 剩余车位数
     */
    @ApiModelProperty("剩余车位数量")
    private Integer leftAmount;

    /**
     * 停车场名
     */
    @ApiModelProperty("停车场名称")
    private String name;

    /**
     * 管理员
     */
    @ApiModelProperty("停车场管理员名")
    private String administrator;

    /**
     * 管理员联系方式
     */
    @ApiModelProperty("管理员电话")
    private String phone;

    /**
     * 停车场位置
     */
    @ApiModelProperty("停车场地址")
    private String address;


}
