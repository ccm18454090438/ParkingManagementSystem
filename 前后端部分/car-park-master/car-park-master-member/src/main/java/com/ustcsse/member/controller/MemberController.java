package com.ustcsse.member.controller;

import com.ustcsse.common.model.R;
import com.ustcsse.member.model.MemberQueryParams;
import com.ustcsse.member.model.po.Member;
import com.ustcsse.member.model.vo.MemberVo;
import com.ustcsse.member.service.MemberService;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@RestController
@RequestMapping("member")
@Api(value = "停车会员管理接口", tags = "停车会员管理接口")
public class MemberController {

    @Autowired
    private MemberService  memberService;

    @ApiOperation("查询全部停车会员接口-带查询参数")
    @PostMapping("/getAll")
    public R getAll(@RequestBody MemberQueryParams queryParams){
        PageParams pageParams = queryParams.getPageParams();
        PageResult<MemberVo> pageResult = memberService.getAll(pageParams, queryParams);
        return R.ok(pageResult);
    }

    @ApiOperation("查询全部停车会员接口-不带查询参数")
    @GetMapping("/getAll")
    public R getAll(){
        List<Member> memberList = memberService.list();
        return R.ok(memberList);
    }

    @ApiOperation("查询单个停车会员接口")
    @GetMapping("/getById/{id}")
    public Member getById(@PathVariable("id") String id){
        Member member = memberService.getById(id);
        return member;
    }

    @ApiOperation("查询单个停车会员接口")
    @GetMapping("/getByCarplate")
    public Member getByCarplate(@RequestParam String carplate){
        Member member = memberService.getByCarplate(carplate);
        return member;
    }

    @ApiOperation("新增停车会员接口")
    @PostMapping("/add")
    public R addMember(@RequestBody MemberVo memberVo){
        Member member = new Member();
        member.setName(memberVo.getName());
        member.setCarplate(memberVo.getCarplate());
        member.setParkinglotName(memberVo.getParkinglotName());
        member.setPhone(memberVo.getPhone());
        if(memberVo.getTypeName() != null){
            if(memberVo.getTypeName().equals("月卡")){
                member.setType(1);
                if(memberVo.getId() == null){
                    member.setDayleft(30);
                }
            }else if(memberVo.getTypeName().equals("季卡")){
                member.setType(2);
                if(memberVo.getId() == null){
                    member.setDayleft(120);
                }
            }else{
                member.setType(3);
                if(memberVo.getId() == null){
                    member.setDayleft(365);
                }
            }
        }
        //TODO: 根据停车场Name获得停车场id，进行属性设置
        if(memberVo.getId() == null){
            member.setBasedate(LocalDate.now());
            member.setCreatetime(LocalDate.now());
            memberService.save(member);
        }else{
            member.setId(memberVo.getId());
            member.setDayleft(memberVo.getDayleft());
            member.setBasedate(memberVo.getBasedate());
            member.setCreatetime(memberVo.getCreatetime());
            memberService.updateById(member);
        }
        return R.ok();
    }

    @ApiOperation("修改会员信息接口")
    @PostMapping("/update")
    public R updateMember(@RequestBody Member member){
        memberService.updateById(member);
        return R.ok();
    }

    @ApiOperation("删除会员接口")
    @DeleteMapping("/delete/{id}")
    public R delateById(@PathVariable("id") String id){
        memberService.removeById(id);
        return R.ok();
    }

}
