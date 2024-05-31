package com.ustcsse.auth.service;

import com.ustcsse.auth.model.dto.UserDto;
import com.ustcsse.auth.model.po.User;
import com.ustcsse.common.model.R;

public interface LoginServcie {
    R login(UserDto userDto);

    R logout();

}
