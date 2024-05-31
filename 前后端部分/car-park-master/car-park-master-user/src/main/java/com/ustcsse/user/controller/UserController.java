package com.ustcsse.user.controller;

import com.ustcsse.common.model.R;
import com.ustcsse.user.model.dto.UserDto;
import com.ustcsse.user.model.po.User;
import com.ustcsse.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("user")
@Api(value = "停车系统管理接口", tags = "停车系统管理接口")
public class UserController {

    @Autowired
    private UserService  userService;

    @ApiOperation("登录校验接口")
    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto){
        User user = userService.login(userDto);
        if(user == null){
            return R.error("认证失败，请重试！");
        }
        return R.ok(user);
    }

    @ApiOperation("根据id获取管理员信息")
    @GetMapping("/getById/{id}")
    public User getById(@PathVariable("id") String id){
        User user = userService.getById(id);
        return user;
    }

    @ApiOperation("新增管理员接口")
    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        userService.save(user);
        return;
    }

    @ApiOperation("修改管理员信息接口")
    @PostMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.updateById(user);
        return;
    }

}
