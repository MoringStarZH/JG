package com.example.gfjc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gfjc.Form.LogInForm;
import com.example.gfjc.Form.UserLoginForm;
import com.example.gfjc.Pojo.Authority;
import com.example.gfjc.Pojo.User;
import com.example.gfjc.Service.AuthorityService;

import com.example.gfjc.Service.UserService;
import com.example.gfjc.Utils.JwtTokenUtils;

import com.example.gfjc.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.Duration;

/**
 * @title UserController
 * @Author: ZKY
 * @CreateTime: 2024-04-01  15:07
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RedisTemplate<Object,User> redisTemplate;
    @ApiOperation("根据用户id查询用户信息")
    @GetMapping("/getById/{id}")
    public Result<UserLoginForm> getById(@PathVariable Long id){
        User user = userService.getById(id);
        if (user == null){
            return Result.error("发生错误");
        }
        LambdaQueryWrapper<Authority> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Authority::getJob, user.getJob());
        Authority authority = authorityService.getOne(queryWrapper1);
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setUser(user);
        userLoginForm.setAuthority(authority);

        return Result.success(userLoginForm);
    }


    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user){
        String phone = user.getPhone();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User u = userService.getOne(queryWrapper);
        if(u != null){
            return Result.error("注册失败，该手机号码已被注册");
        }else{
            String password = user.getPassword();
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(password);
            log.info(user.getNickName());
            if(user.getNickName() == null || user.getNickName().equals("")){
                user.setNickName("User"+user.getPhone());
            }
            user.setStatus(1);
            userService.save(user);
        }
        return Result.success("注册成功");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginForm> login(HttpServletRequest request, @RequestBody User user){
        log.info(user.toString());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        User user1 = userService.getOne(queryWrapper);


        if(user1 == null){
            return Result.error("用户未注册");
        }

        if (!user1.getPassword().equals(password)){
            log.info(user1.toString());
            return Result.error("账号或密码错误");
        }

        if (user1.getStatus() == 0){
            return Result.error("您不被允许登录此平台");
        }

        request.getSession().setAttribute("user",user1.getId());

//        BaseContext.setCurrentId(user1.getId());
//        log.info(BaseContext.getCurrentId()+"");


        LambdaQueryWrapper<Authority> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Authority::getJob, user1.getJob());
        Authority authority = authorityService.getOne(queryWrapper1);
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setUser(user1);
        userLoginForm.setAuthority(authority);

        return Result.success(userLoginForm);
    }

    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");
    }

    @ApiOperation("用户进行分页查询展示")
    @GetMapping("/page")
    public Result<Page<User>> page(Integer page, Integer pageSize, String name, String job){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<User> pageInfo = new Page<User>(page,pageSize);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), User::getNickName,name);
        queryWrapper.eq(StringUtils.isNotEmpty(job), User::getJob,job);
        queryWrapper.orderByDesc(User::getUpdateTime);


        userService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update/{id}")
    public Result<String> update(@RequestBody User user, @PathVariable Long id){
        user.setId(id);
        log.info(user.toString());
        if (user.getPassword()!=null){
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        userService.updateById(user);
        return Result.success("信息修改成功");
    }

    @ApiOperation("小程序端用户登录")
    @PostMapping("/frontend/login")
    public Result<LogInForm> login(@RequestBody User user){
        log.info(user.toString());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        User user1 = userService.getOne(queryWrapper);
        if (user1 == null) {
            return Result.error("用户未注册");
        }
        LogInForm logInForm = new LogInForm();
        String jwtToken = JwtTokenUtils.generateJwtToken(user);
        redisTemplate.opsForValue().set(jwtToken,user1, Duration.ofMinutes(120L));
        logInForm.setJwtToken(jwtToken);
        logInForm.setUser(user1);
        return Result.success(logInForm);
    }
}
