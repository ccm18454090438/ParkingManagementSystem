package com.ustcsse.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustcsse.member.mapper.MemberMapper;
import com.ustcsse.member.model.MemberQueryParams;
import com.ustcsse.member.model.po.Member;
import com.ustcsse.member.model.vo.MemberVo;
import com.ustcsse.member.service.MemberService;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Override
    public PageResult<MemberVo> getAll(PageParams pageParams, MemberQueryParams queryParams) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryParams.getName()), Member::getName, queryParams.getName());
        queryWrapper.like(StringUtils.isNotEmpty(queryParams.getCarplate()), Member::getCarplate, queryParams.getCarplate());
        queryWrapper.like(StringUtils.isNotEmpty(queryParams.getPhone()), Member::getPhone, queryParams.getPhone());
        if(queryParams.getParkinglotName() != null && !queryParams.getParkinglotName().equals("")){
            queryWrapper.eq(Member::getParkinglotName, queryParams.getParkinglotName());
        }
        Page<Member> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Member> result = memberMapper.selectPage(page, queryWrapper);
        List<Member> items = result.getRecords();
        List<MemberVo> itemsVo = new ArrayList<>();
        for(Member item : items){
            MemberVo vo = new MemberVo();
            BeanUtils.copyProperties(item, vo);
            if(vo.getType() == 1){
                vo.setTypeName("月卡");
            }else if(vo.getType() == 2){
                vo.setTypeName("季卡");
            }else{
                vo.setTypeName("年卡");
            }
            itemsVo.add(vo);
        }
        int total = (int)result.getTotal();
        PageResult<MemberVo> pageResult = new PageResult<>(itemsVo, total, pageParams.getPageNo(), pageParams.getPageSize());
        return pageResult;
    }

    @Override
    public Member getByCarplate(String carplate) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getCarplate, carplate);
        Member member = this.getOne(queryWrapper);
        return member;
    }
}
