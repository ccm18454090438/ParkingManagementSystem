package com.ustcsse.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.auth.model.dto.UserDto;
import com.ustcsse.auth.model.po.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-04-05
 */
public interface UserService extends IService<User> {

    User login(UserDto userDto);
}
