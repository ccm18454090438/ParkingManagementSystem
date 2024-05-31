package com.ustcsse.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.common.model.R;
import com.ustcsse.user.model.dto.UserDto;
import com.ustcsse.user.model.po.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-03-28
 */
public interface UserService extends IService<User> {

    User login(UserDto userDto);
}
