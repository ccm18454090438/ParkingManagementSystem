package com.ustcsse.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustcsse.order.model.po.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ustcsse
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
