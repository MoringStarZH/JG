package com.example.jg.Form;

import com.example.jg.Pojo.Authority;
import com.example.jg.Pojo.User;
import lombok.Data;

/**
 * @title UserLoginForm
 * @Author: ZKY
 * @CreateTime: 2024-04-01  16:05
 * @Description: TODO
 */
@Data
public class UserLoginForm {
    User user;

    Authority authority;
}
