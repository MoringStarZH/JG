package com.example.gfjc.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gfjc.Pojo.Enterprise;
import jdk.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @title Enterprise
 * @Author: ZKY
 * @CreateTime: 2024-04-01  17:18
 * @Description: TODO
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {
    @Select("SELECT name FROM enterprise")
    List<String> findAllName();
}
