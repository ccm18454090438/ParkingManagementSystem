package com.ustcsse.parkinglot.service.impl;

import com.ustcsse.parkinglot.model.po.Parkinglot;
import com.ustcsse.parkinglot.mapper.ParkinglotMapper;
import com.ustcsse.parkinglot.service.ParkinglotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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

}
