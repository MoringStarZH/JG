package com.example.jg.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jg.Pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @title UserMapper
 * @Author: ZKY
 * @CreateTime: 2023-12-25  16:45
 * @Description: TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where open_id=#{openid}")
    User selectByOpenId(String openid);

    @Select("SELECT * from user where phone=#{phone}")
    User selectByPhone(String phone);
}
