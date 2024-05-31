package com.ustcsse.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * ClassName: WebSocketConfig
 * Package: com.ustcsse.parking.config
 * Description:
 *
 * @Author CoderMountain
 * @Create 2024/4/9 10:11
 * @Version 1.0
 */
@Configuration
public class WebSocketConfig {
    /**
     * 主要作用是自动注册使用了 @ServerEndpoint 注解的 WebSocket endpoint。
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
