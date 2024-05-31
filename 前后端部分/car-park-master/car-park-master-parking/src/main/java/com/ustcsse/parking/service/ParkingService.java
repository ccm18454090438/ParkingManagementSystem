package com.ustcsse.parking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.parking.model.ParkingQueryParams;
import com.ustcsse.parking.model.po.Parking;
import com.ustcsse.parking.model.vo.ParkingVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-03-28
 */
public interface ParkingService extends IService<Parking> {

    PageResult<Parking> getAll(PageParams pageParams, ParkingQueryParams queryParams);

    void add(ParkingVo parkingVo);

    void update(ParkingVo parkingVo);

    PageResult<Parking> getParkingCar(PageParams pageParams, ParkingQueryParams queryParams);

    R parkingIn(ParkingVo parkingVo);

    R parkingOut(ParkingVo parkingVo);

    Parking getByParkingVo(ParkingVo parkingVo);
}
