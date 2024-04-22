package com.example.jg.Form;

import com.example.jg.Pojo.User;
import lombok.Data;

@Data
public class LogInForm {
    String jwtToken;

    User user;
}