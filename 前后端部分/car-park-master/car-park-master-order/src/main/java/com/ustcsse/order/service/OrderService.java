package com.ustcsse.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.order.model.OrderRequestParams;
import com.ustcsse.order.model.po.Order;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-03-28
 */
public interface OrderService extends IService<Order> {

    PageResult<Order> getAll(PageParams pageParams, OrderRequestParams requestParams);

    String saveOrder(Order order);

    Order getOrderByTradeNo(String tradeNo);

    Order getByTradeId(long id);
}
