package com.ustcsse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CarParkMasterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkMasterGatewayApplication.class, args);
    }

}
