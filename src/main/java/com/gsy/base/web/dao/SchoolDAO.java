package com.gsy.base.web.dao;

import com.gsy.base.web.entity.SchoolEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mrzsh on 2018/4/12.
 */
@Mapper
public interface SchoolDAO {
    @Select({"SELECT",
            "	*, 0 AS all_count",
            "FROM",
            "	school_list",
            "LIMIT #{page},#{count}",
            "UNION",
            "	SELECT",
            "		*, count(id) AS all_count",
            "	FROM",
            "		school_list"})
    List<SchoolEntity> selectAllSchool(@Param("page") int page,@Param("count") int count);

    @Select("SELECT count(id) FROM school_list")
    int count();

    @Select({"SELECT",
            "	*, 0 AS all_count",
            "FROM",
            "	school_list",
            "WHERE school_name LIKE concat(#{name},'%')",
            "LIMIT #{page},#{count}",
            "UNION",
            "	SELECT",
            "		*, count(id) AS all_count",
            "	FROM",
            "		school_list",
            "  WHERE school_name LIKE concat(#{name},'%')"
            })
    List<SchoolEntity> selectAllSchoolByName(@Param("name") String name,@Param("page") int page,@Param("count") int count);


}
