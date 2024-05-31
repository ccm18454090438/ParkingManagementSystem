package com.ustcsse.auth.service.impl;

import com.ustcsse.auth.model.LoginUser;
import com.ustcsse.auth.model.dto.UserDto;
import com.ustcsse.auth.service.LoginServcie;
import com.ustcsse.auth.utils.JwtUtil;
import com.ustcsse.common.model.R;
import com.ustcsse.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;


    @Override
    public R login(UserDto userDto) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //认证通过存入SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid, 36000000L);
        userDto.setRole("管理员");
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("user", userDto);
        //把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject("login:", loginUser);
        return R.ok(map);
    }

    @Override
    public R logout() {
        redisCache.deleteObject("login:");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
//            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//            Integer userid = loginUser.getUser().getId();
//            // Delete the value from redis
//            redisCache.deleteObject("login:");
//            return R.ok();
//        } else {
//            return R.error("用户未认证！");
//        }
        return R.ok();
    }
}
