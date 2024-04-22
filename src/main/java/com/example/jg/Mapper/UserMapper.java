package com.example.jg.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jg.Pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title UserMapper
 * @Author: ZKY
 * @CreateTime: 2023-12-25  16:45
 * @Description: TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
