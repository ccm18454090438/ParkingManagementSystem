package com.ustcsse.parkinglot.controller;

import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.parkinglot.model.po.Parkinglot;
import com.ustcsse.parkinglot.service.ParkinglotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@RestController
@RequestMapping("parkinglots")
@Api(value = "停车场管理接口", tags = "停车场管理接口")
public class ParkinglotsController {

    @Autowired
    private ParkinglotService  parkinglotService;

    @ApiOperation("查询所有停车场信息列表接口")
    @GetMapping("/getAll")
    public R getAll(){
        List<Parkinglot> list  = parkinglotService.list();
        return R.ok(list);
    }

    @ApiOperation("分页查询所有停车场信息列表接口")
    @PostMapping("/getAll")
    public R getAll(@RequestBody PageParams pageParams){
        PageResult<Parkinglot> pageResult  = parkinglotService.getAll(pageParams);
        return R.ok(pageResult);
    }

    @ApiOperation("查询单个停车场信息接口-id查询")
    @GetMapping("/getById/{id}")
    public Parkinglot getById(@PathVariable("id") String id){
        Parkinglot parkinglot = parkinglotService.getById(id);
        return parkinglot;
    }

    @ApiOperation("查询单个停车场信息接口-名称查询")
    @GetMapping("/getByName/{name}")
    public Parkinglot getByName(@PathVariable("name") String name){
        Parkinglot parkinglot = parkinglotService.getByName(name);
        return parkinglot;
    }

    @ApiOperation("新增/按Id更新停车场信息接口")
    @PostMapping("/add")
    public R addParkinglot(@RequestBody Parkinglot parkinglot){
        if(parkinglot.getTotal() != null && parkinglot.getLeftAmount() != null){
            int carAmount = parkinglot.getTotal() - parkinglot.getLeftAmount();
            parkinglot.setCarAmount(carAmount);
        }
        if(parkinglot.getId() == null){
            parkinglotService.save(parkinglot);
        }else{
            parkinglotService.updateById(parkinglot);
        }
        return R.ok();
    }

    @ApiOperation("修改停车场信息接口")
    @PostMapping("/update")
    public R updateParkinglot(@RequestBody Parkinglot parkinglot){
        parkinglotService.updateById(parkinglot);
        return R.ok();
    }

    @ApiOperation("删除停车场信息接口")
    @DeleteMapping("/delete/{id}")
    public R delateById(@PathVariable("id") String id){
        parkinglotService.removeById(id);
        return R.ok();
    }
}
