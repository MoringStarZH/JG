package com.example.gfjc.Form;

import com.example.gfjc.Pojo.Authority;
import com.example.gfjc.Pojo.User;
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
