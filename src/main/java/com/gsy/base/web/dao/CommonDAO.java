package com.gsy.base.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by mrzsh on 2018/2/8.
 */
@Mapper
public interface CommonDAO {
    @Select("SELECT dict_value FROM dict_table WHERE dict_name = #{name}")
    int selectConst(String name);
}
