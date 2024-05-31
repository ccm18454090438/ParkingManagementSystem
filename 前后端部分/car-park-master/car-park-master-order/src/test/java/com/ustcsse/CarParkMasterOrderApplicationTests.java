package com.ustcsse;

import com.ustcsse.common.utils.IdWorkerUtils;
import com.ustcsse.order.model.po.Order;
import com.ustcsse.order.service.AlipayService;
import com.ustcsse.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarParkMasterOrderApplicationTests {
    @Autowired
    OrderService orderService;

    @Autowired
    AlipayService alipayService;
    @Test
    void testOrderSevice() {
        Order order = new Order();
        order.setAmount(20.00);
        order.setCarplate("皖A12312");
        order.setDuration(14400L);
        order.setMethod(1);
        order.setType(0);
        order.setParkingid(10);
        order.setParkinglotid(1);
        orderService.save(order);
    }

    @Test
    void testAlipay() throws Exception {
        Order order = new Order();
        order.setAmount(0.01);
        order.setParkingid(10);
        order.setTradeNo(IdWorkerUtils.getInstance().nextId());
        order.setCarplate("皖A88888");
        String payCode = alipayService.pay(order);
        System.out.println(payCode);
    }

}
