package com.ustcsse.auth.model.dto;

import lombok.Data;

@Data
public class UserDto {
    /**
     * 管理员姓名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

    private String role;

}
