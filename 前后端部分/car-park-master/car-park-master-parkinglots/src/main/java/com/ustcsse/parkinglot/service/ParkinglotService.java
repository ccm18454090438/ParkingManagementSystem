package com.ustcsse.parkinglot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.parkinglot.model.po.Parkinglot;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-03-28
 */
public interface ParkinglotService extends IService<Parkinglot> {

    PageResult<Parkinglot> getAll(PageParams pageParams);

    Parkinglot getByName(String name);
}
