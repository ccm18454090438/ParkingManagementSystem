package com.ustcsse.parking.service.impl;

import com.ustcsse.parking.model.po.Parking;
import com.ustcsse.parking.mapper.ParkingMapper;
import com.ustcsse.parking.service.ParkingService;
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
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {

}
