package com.ustcsse.auth.controller;

import com.alibaba.fastjson.JSON;
import com.ustcsse.auth.model.LoginUser;
import com.ustcsse.auth.model.dto.UserDto;
import com.ustcsse.auth.model.po.User;
import com.ustcsse.auth.service.LoginServcie;
import com.ustcsse.auth.utils.JwtUtil;
import com.ustcsse.common.model.R;
import com.ustcsse.common.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;

    @Autowired
    RedisCache redisCache;

    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto){
        //登录
        return loginServcie.login(userDto);
    }

    @RequestMapping("/logout")
    public R logout(){
        return loginServcie.logout();
    }

    @PostMapping("/check")
    public R check(@RequestParam("token") String token){
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("token无效！");
        }
        //从redis中获取用户信息
        String redisKey = "login:";
        LoginUser loginUser = JSON.toJavaObject(redisCache.getCacheObject(redisKey), LoginUser.class);
        if(Objects.isNull(loginUser)){
            return R.error("用户未登陆!");
        }

        return R.ok("认证通过");
    }
}
