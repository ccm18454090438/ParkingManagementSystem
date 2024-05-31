package com.ustcsse.parking.feign;

import com.ustcsse.common.model.R;
import com.ustcsse.parking.model.po.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cpm-order", path = "/order")
public interface OrderFeignService {

    @PostMapping("/addToPay")
    public String addOrderToPay(@RequestBody Order order);

    @PostMapping("/add")
    public R addOrder(@RequestBody Order order);

    @GetMapping("/getByTradeNo")
    public Order getOrderByTradeNo(String tradeNo);
}
