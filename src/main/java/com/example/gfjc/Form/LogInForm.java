package com.example.gfjc.Form;

import com.example.gfjc.Pojo.User;
import lombok.Data;

@Data
public class LogInForm {
    String jwtToken;

    User user;
}