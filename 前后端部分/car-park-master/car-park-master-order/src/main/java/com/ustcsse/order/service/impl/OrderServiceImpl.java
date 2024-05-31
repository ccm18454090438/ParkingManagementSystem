package com.ustcsse.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.order.mapper.OrderMapper;
import com.ustcsse.order.model.OrderRequestParams;
import com.ustcsse.order.model.po.Order;
import com.ustcsse.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AlipayServiceImpl alipayService;

    @Override
    public PageResult<Order> getAll(PageParams pageParams, OrderRequestParams requestParams) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(requestParams.getCarplate()), Order::getCarplate, requestParams.getCarplate());
        if(requestParams.getParkinglotName() != null && !requestParams.getParkinglotName().equals("")){
            queryWrapper.eq(Order::getParkinglotName, requestParams.getParkinglotName());
        }
        if(requestParams.getMethod() != null && !requestParams.getMethod().equals("")){
            queryWrapper.eq(Order::getMethod, Integer.parseInt(requestParams.getMethod()));
        }
        if(requestParams.getType() != null && !requestParams.getType().equals("")){
            queryWrapper.eq(Order::getType, Integer.parseInt(requestParams.getType()));
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(requestParams.getDateRange() != null && requestParams.getDateRange().length > 0){
            if(requestParams.getDateRange()[0] != null && !requestParams.getDateRange()[0].equals("")){
                LocalDateTime begin = LocalDateTime.parse(requestParams.getDateRange()[0], format);
                queryWrapper.ge(Order::getTime, begin);
            }
            if(requestParams.getDateRange()[1] != null && !requestParams.getDateRange()[1].equals("")){
                LocalDateTime end = LocalDateTime.parse(requestParams.getDateRange()[1], format);
                queryWrapper.le(Order::getTime, end);
            }
        }
        Page<Order> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Order> result = orderMapper.selectPage(page, queryWrapper);
        List<Order> items = result.getRecords();
        int total = (int)result.getTotal();
        PageResult<Order> pageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return pageResult;
    }

    @Override
    public String saveOrder(Order order) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getTradeNo, order.getTradeNo());
        Order check = getOne(queryWrapper);
        if(check != null){
            throw new RuntimeException("订单已存在！");
        }
        String payCode = "";
        try{
            payCode = alipayService.pay(order);
            System.out.println("拿到支付二维码：" + payCode);
            save(order);
        }catch (Exception e){
            throw new RuntimeException("alipay故障！");
        }
        return payCode;
    }

    @Override
    public Order getOrderByTradeNo(String tradeNo) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getTradeNo, tradeNo);
        Order order = this.getOne(queryWrapper);
        return order;
    }

    @Override
    public Order getByTradeId(long id) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getTradeNo, id);
        Order order = getOne(queryWrapper);
        return order;
    }


}
