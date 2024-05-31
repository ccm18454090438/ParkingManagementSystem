package com.ustcsse.parkinglot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.parkinglot.mapper.ParkinglotMapper;
import com.ustcsse.parkinglot.model.po.Parkinglot;
import com.ustcsse.parkinglot.service.ParkinglotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ParkinglotServiceImpl extends ServiceImpl<ParkinglotMapper, Parkinglot> implements ParkinglotService {
    @Autowired
    ParkinglotMapper parkinglotMapper;
    @Override
    public PageResult<Parkinglot> getAll(PageParams pageParams) {
        LambdaQueryWrapper<Parkinglot> queryWrapper = new LambdaQueryWrapper<>();
        Page<Parkinglot> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Parkinglot> result = parkinglotMapper.selectPage(page, queryWrapper);
        List<Parkinglot> items = result.getRecords();
        int total = (int)result.getTotal();
        PageResult<Parkinglot> pageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return pageResult;
    }

    @Override
    public Parkinglot getByName(String name) {
        LambdaQueryWrapper<Parkinglot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Parkinglot::getName, name);
        Parkinglot parkinglot = this.getOne(queryWrapper);
        return parkinglot;
    }
}
