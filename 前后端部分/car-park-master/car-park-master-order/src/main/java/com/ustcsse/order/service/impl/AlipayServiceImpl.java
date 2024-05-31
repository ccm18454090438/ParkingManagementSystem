package com.ustcsse.order.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.ustcsse.common.utils.QRCodeUtil;
import com.ustcsse.order.model.po.Order;
import com.ustcsse.order.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * ClassName: AlipayServiceImpl
 * Package: com.ustcsse.order.service.impl
 * Description:
 *
 * @Author CoderMountain
 * @Create 2024/4/10 15:45
 * @Version 1.0
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {
    @Resource
    private Config config;

    @Override
    public String pay(Order order) throws Exception {
        Factory.setOptions(config);
        //调用支付宝的接口
        AlipayTradePrecreateResponse payResponse = Factory.Payment.FaceToFace().preCreate(order.getCarplate(), String.valueOf(order.getTradeNo()),  String.valueOf(order.getAmount()));
        //参照官方文档响应示例，解析返回结果
        String httpBodyStr = payResponse.getHttpBody();
        JSONObject jsonObject = JSONObject.parseObject(httpBodyStr);
        String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
        //File png = QrCodeUtil.generate(qrUrl, 300, 300, new File("png"));
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        String qrCode = qrCodeUtil.createQRCode(qrUrl, 300, 300);
        return qrCode;
    }
}
