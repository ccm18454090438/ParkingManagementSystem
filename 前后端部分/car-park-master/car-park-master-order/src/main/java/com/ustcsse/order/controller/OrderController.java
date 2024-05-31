package com.ustcsse.order.controller;

import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.order.model.OrderRequestParams;
import com.ustcsse.order.model.dto.OrderDto;
import com.ustcsse.order.model.po.Order;
import com.ustcsse.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@RestController
@RequestMapping("order")
@Api(value = "停车订单管理接口", tags = "停车订单管理接口")
public class OrderController {

    @Autowired
    private OrderService  orderService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("查询所有订单列表接口")
    @PostMapping("/getAll")
    public R getAll(@RequestBody OrderRequestParams requestParams){
        PageParams pageParams = requestParams.getPageParams();
        PageResult<Order> page = orderService.getAll(pageParams, requestParams);
        return R.ok(page);
    }

    @ApiOperation("查询单个订单信息接口")
    @GetMapping("/getById/{id}")
    public Order getById(@PathVariable("id") String id){
        Order order = orderService.getById(id);
        return order;
    }

    @ApiOperation("查询单个订单信息接口-根据交易单号")
    @GetMapping("/getByTradeNo")
    public Order getOrderByTradeNo(String tradeNo){
        Order order = orderService.getOrderByTradeNo(tradeNo);
        return order;
    }

    @ApiOperation("新增停车订单接口,需要获取支付码")
    @PostMapping("/addToPay")
    public String addOrderToPay(@RequestBody Order order){
        String result = orderService.saveOrder(order);
        //log.info("向redis中存入二维码信息" + result);
        return result;
    }

    @ApiOperation("新增停车订单接口,无需支付")
    @PostMapping("/add")
    public R addOrder(@RequestBody Order order) {
        orderService.save(order);
        return R.ok();
    }

    @ApiOperation("修改订单信息接口")
    @PostMapping("/update")
    public void updateOrder(@RequestBody Order order){
        orderService.updateById(order);
        return;
    }

    @ApiOperation("删除订单接口")
    @DeleteMapping("/delete/{id}")
    public void delateById(@PathVariable("id") String id){
        orderService.removeById(id);
        return;
    }

    @ApiOperation("通过订单号查询订单")
    @GetMapping("/getByTradeId/{id}")
    public R getByTradeId(@PathVariable("id") long id){
        Order order = orderService.getByTradeId(id);
        return R.ok(order);
    }

}
