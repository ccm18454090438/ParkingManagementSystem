package com.ustcsse;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.ustcsse.order.mapper")
@EnableSwagger2Doc
public class CarParkMasterOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkMasterOrderApplication.class, args);
    }

}
