package com.ustcsse;

import com.alibaba.fastjson.TypeReference;
import com.ustcsse.common.model.R;
import com.ustcsse.parking.feign.ParkinglotsFeignService;
import com.ustcsse.parking.model.po.Parkinglot;
import com.ustcsse.parking.service.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CarParkMasterParkingApplicationTests {

    @Autowired
    ParkinglotsFeignService parkinglotsFeignService;

    @Autowired
    WebSocketServer webSocketServer;

    @Test
    void testParkinglotsFeign() {
        R result = parkinglotsFeignService.getAll();
        TypeReference<List<Parkinglot>> typeReference = new TypeReference<List<Parkinglot>>() {};
        List<Parkinglot> parkingLots = result.getData(typeReference);
        for(Parkinglot parkinglot : parkingLots){
            System.out.println(parkinglot);
        }
        return;
    }


}
