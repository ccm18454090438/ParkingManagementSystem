package com.ustcsse.order.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.ustcsse.common.utils.IdWorkerUtils;
import com.ustcsse.order.config.PayNotifyConfig;
import com.ustcsse.order.model.po.Order;
import com.ustcsse.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("order")
public class PayController {

    @Resource
    private Config config;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderService orderService;
    /**
     * 收银台点击结账
     * 发起下单请求
     */
    @GetMapping("/pay")
    public void pay(HttpServletResponse response) throws Exception {
        Factory.setOptions(config);
        Order order = new Order();
        order.setAmount(0.01);
        order.setParkingid(10);
        order.setTradeNo(IdWorkerUtils.getInstance().nextId());
        order.setCarplate("皖A88888");
        //调用支付宝的接口
        AlipayTradePrecreateResponse payResponse = Factory.Payment.FaceToFace().preCreate(order.getCarplate(), String.valueOf(order.getTradeNo()),  String.valueOf(order.getAmount()));
        //参照官方文档响应示例，解析返回结果
        String httpBodyStr = payResponse.getHttpBody();
        JSONObject jsonObject = JSONObject.parseObject(httpBodyStr);
        String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
        QrCodeUtil.generate(qrUrl, 300, 300, "png", response.getOutputStream());
    }

    /**
     * 给支付宝的回调接口
     */
    @PostMapping("/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<>();
        //获取支付宝POST过来反馈信息，将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String name : parameterMap.keySet()) {
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //验签
        //Boolean signResult = Factory.Payment.Common().verifyNotify(params);
        log.info("收到支付宝发送的支付结果通知");
        String out_trade_no = request.getParameter("out_trade_no");
        log.info("交易流水号：{}", out_trade_no);
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //交易成功
        switch (trade_status) {
            case "TRADE_SUCCESS":
                //支付成功的业务逻辑，比如落库，开vip权限等
                Order order = orderService.getOrderByTradeNo(out_trade_no);
                String orderMsg = JSON.toJSONString(order);
                rabbitTemplate.convertAndSend(PayNotifyConfig.PAYNOTIFY_EXCHANGE_FANOUT, "", 1 + orderMsg);
                order.setStatus(1);
                orderService.updateById(order);
                break;
            case "TRADE_FINISHED":
                log.info("交易结束，不可退款");
                //其余业务逻辑
                break;
            case "TRADE_CLOSED":
                log.info("超时未支付，交易已关闭，或支付完成后全额退款");
                rabbitTemplate.convertAndSend(PayNotifyConfig.PAYNOTIFY_EXCHANGE_FANOUT, "", 0 + out_trade_no);
                //其余业务逻辑
                break;
            case "WAIT_BUYER_PAY":
                log.info("交易创建，等待买家付款");
                //其余业务逻辑
                break;
        }
        response.getWriter().write("success");   //返回success给支付宝，表示消息我已收到，不用重调

        //response.getWriter().write("fail");   ///返回fail给支付宝，表示消息我没收到，请重试
    }
}
