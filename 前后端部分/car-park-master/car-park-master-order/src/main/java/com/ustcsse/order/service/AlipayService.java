package com.ustcsse.order.service;

import com.ustcsse.order.model.po.Order;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ClassName: AlipayService
 * Package: com.ustcsse.order.service.impl
 * Description:
 *
 * @Author CoderMountain
 * @Create 2024/4/10 15:45
 * @Version 1.0
 */
public interface AlipayService {

    public String pay(@RequestBody Order order) throws Exception;

}
