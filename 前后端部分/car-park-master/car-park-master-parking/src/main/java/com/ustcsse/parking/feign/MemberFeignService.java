package com.ustcsse.parking.feign;

import com.ustcsse.common.model.R;
import com.ustcsse.parking.model.po.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cpm-member", path = "/member")
public interface MemberFeignService {

    @GetMapping("/getAll")
    public R getAll();

    @GetMapping("/getByCarplate")
    public Member getByCarplate(@RequestParam(value = "carplate") String carplate);
}
