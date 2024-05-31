package com.ustcsse.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustcsse.common.model.R;
import com.ustcsse.user.mapper.UserMapper;
import com.ustcsse.user.model.dto.UserDto;
import com.ustcsse.user.model.po.User;
import com.ustcsse.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(UserDto userDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDto.getUsername());
        queryWrapper.eq(User::getPassword, userDto.getPassword());
        User one = userMapper.selectOne(queryWrapper);
        if(one != null && one.getStatus() == 1){
            one.setPassword(null);
            return one;
        }
        return null;
    }
}
