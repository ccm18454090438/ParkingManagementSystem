package com.ustcsse.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustcsse.auth.model.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ustcsse
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
