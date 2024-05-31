package com.ustcsse.parking.feign;

import com.ustcsse.common.model.R;
import com.ustcsse.parking.model.po.Parkinglot;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cpm-parkinglots", path = "/parkinglots")
public interface ParkinglotsFeignService {

    @GetMapping("/getAll")
    public R getAll();

//    @GetMapping("/getById/{id}")
//    public Parkinglot getById(@PathVariable("id") String id);

    @GetMapping("/getByName/{name}")
    public Parkinglot getByName(@PathVariable(value = "name") String name);

    @PostMapping("/add")
    public R addParkinglot(@RequestBody Parkinglot parkinglot);
}
