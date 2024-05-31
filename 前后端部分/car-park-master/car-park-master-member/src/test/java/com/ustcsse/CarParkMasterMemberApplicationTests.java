package com.ustcsse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ustcsse.member.mapper.MemberMapper;
import com.ustcsse.member.model.po.Member;
import com.ustcsse.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = com.ustcsse.CarParkMasterMemberApplication.class)
class CarParkMasterMemberApplicationTests {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberServiceImpl memberService;

    @Test
    void testMemberMapper() {
//        Member member = memberMapper.selectPage(1);
//        System.out.println(member.toString());

        //分页查询
        Page<Member> page = new Page<Member>(2, 3);
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        Page<Member> pageResult = memberMapper.selectPage(page, queryWrapper);
        List<Member> records = pageResult.getRecords();
        System.out.println(records);
    }

    @Test
    void testAdd(){
        Member member = new Member();
        member.setBasedate(LocalDate.now());
        member.setCreatetime(LocalDate.now());
        member.setParkinglotid(2);
        member.setDayleft(120);
        memberService.save(member);
    }

}
