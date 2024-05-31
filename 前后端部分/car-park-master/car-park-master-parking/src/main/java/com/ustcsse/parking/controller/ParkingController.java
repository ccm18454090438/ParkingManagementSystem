package com.ustcsse.parking.controller;

import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.parking.model.ParkingQueryParams;
import com.ustcsse.parking.model.po.Parking;
import com.ustcsse.parking.model.vo.ParkingVo;
import com.ustcsse.parking.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@RestController
@RequestMapping("parking")
@Api(value = "停车业务管理接口", tags = "停车业务管理接口")
public class ParkingController {

    @Autowired
    private ParkingService  parkingService;

    @ApiOperation("查询所有停车信息列表接口")
    @PostMapping("/getAll")
    public R getAll(@RequestBody ParkingQueryParams queryParams){
        PageParams pageParams = queryParams.getPageParams();
        PageResult<Parking> pageResult = parkingService.getAll(pageParams, queryParams);
        return R.ok(pageResult);
    }

    @ApiOperation("查询单个停车信息接口-id查询")
    @GetMapping("/getById/{id}")
    public Parking getById(@PathVariable("id") String id){
        Parking parking = parkingService.getById(id);
        return parking;
    }

    @ApiOperation("查询单个停车信息接口-车牌号查询")
    @GetMapping("/getByParkingVo")
    public Parking getByParkingVo(@RequestBody ParkingVo parkingVo){
        Parking parking = parkingService.getByParkingVo(parkingVo);
        return parking;
    }

    @ApiOperation("新增停车记录接口")
    @PostMapping("/add")
    public void addParking(@RequestBody ParkingVo parkingVo){
        parkingService.add(parkingVo);
        return;
    }

    @ApiOperation("车辆出场修改停车记录接口")
    @PostMapping("/update")
    public void updateParking(@RequestBody ParkingVo parkingVo){
        parkingService.update(parkingVo);
        return;
    }

    @ApiOperation("删除停车信息接口")
    @DeleteMapping("/delete/{id}")
    public void delateById(@PathVariable("id") String id){
        parkingService.removeById(id);
        return;
    }

//    @ApiOperation("查询在库车辆接口")
//    @PostMapping("/getParking")
//    public R getParking(@RequestParam PageParams pageParams,@RequestParam ParkingQueryParams queryParams){
//             PageResult<Parking> pageResult = parkingService.getParkingCar(pageParams,queryParams);
//        return R.ok(pageResult);
//    }

    @ApiOperation("查询在库车辆接口")
    @PostMapping("/getParking")
    public R getParking(@RequestBody ParkingQueryParams queryParams){
        PageParams pageParams = queryParams.getPageParams();
        PageResult<Parking> pageResult = parkingService.getParkingCar(pageParams,queryParams);
        return R.ok(pageResult);
    }

    @ApiOperation("车辆入库")
    @PostMapping("/parkingIn")
    public R parkingIn(@RequestBody ParkingVo parkingVo){
        R result = parkingService.parkingIn(parkingVo);
        return result;
    }

    @ApiOperation("车辆出库")
    @PostMapping("/parkingOut")
    public R parkingOut(@RequestBody ParkingVo parkingVo){
        R result = parkingService.parkingOut(parkingVo);
        return result;
    }
}
