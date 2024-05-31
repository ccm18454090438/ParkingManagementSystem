package com.ustcsse.gateway.feign;

import com.ustcsse.gateway.utils.R;
import com.ustcsse.gateway.utils.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cpm-auth", path = "/auth")
public interface AuthFeignService {
    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto);

    @RequestMapping("/logout")
    public R logout();

    @PostMapping("/check")
    public R check(@RequestParam("token") String token);
}
