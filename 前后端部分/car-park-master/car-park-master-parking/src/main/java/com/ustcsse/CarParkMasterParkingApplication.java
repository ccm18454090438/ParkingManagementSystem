package com.ustcsse;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.ustcsse.parking.mapper")
@EnableSwagger2Doc
public class CarParkMasterParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkMasterParkingApplication.class, args);
    }

}
