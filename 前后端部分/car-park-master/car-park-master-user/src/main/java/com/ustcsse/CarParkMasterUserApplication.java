package com.ustcsse;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ustcsse.user.mapper")
@EnableSwagger2Doc
public class CarParkMasterUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkMasterUserApplication.class, args);
    }

}
