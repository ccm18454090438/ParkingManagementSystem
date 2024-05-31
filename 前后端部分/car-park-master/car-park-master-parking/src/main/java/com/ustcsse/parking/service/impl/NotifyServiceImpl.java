package com.ustcsse.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ustcsse.parking.config.PayNotifyConfig;
import com.ustcsse.parking.model.po.Order;
import com.ustcsse.parking.service.NotifyService;
import com.ustcsse.parking.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    WebSocketServer webSocketServer;

    @Override
    @RabbitListener(queues = PayNotifyConfig.PAYNOTIFY_QUEUE)
    public void receiveMessage(String message) {
        String trade = message.substring(1, message.length());
        Order order = JSONObject.parseObject(trade, Order.class);
        log.info("mq收到新消息：" + order.toString());
        Integer parkingid = order.getParkingid();
        Integer parkinglotid = order.getParkinglotid();
        String carplate = order.getCarplate();
        if(message.charAt(0) == '1'){
            //TODO:放行
            webSocketServer.sendOneMessage("1", "[SUCCESS] 临时停车：" + carplate + ",支付成功，谢谢光临！");
        }else{
            webSocketServer.sendOneMessage("1", "[ERROR] 支付失败，请联系管理人员！");
        }

    }
}
