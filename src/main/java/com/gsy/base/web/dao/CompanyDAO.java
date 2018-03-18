package com.gsy.base.web.dao;

import com.gsy.base.web.dto.EnterpriseInfoDTO;
import com.gsy.base.web.entity.merge.EnterpriseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyDAO {

    @Select("<script>SELECT count(id) as count " +
            "FROM enterprise_info " +
            "WHERE " +
            "<if test=\" name != ''\">enterprise_name LIKE #{name} AND </if>" +
            " delete_flag != 1</script>")
    int selectCompanyCount(@Param("name") String name);

    @Select("<script>SELECT " +
            "enterprise_name, state, first_icon_flag," +
            " ls1.label_backgroundcolor AS label_backgroundcolor_1, ls2.label_backgroundcolor AS label_backgroundcolor_2, ls3.label_backgroundcolor AS label_backgroundcolor_3," +
            " ls1.label_color AS label_color_1, ls2.label_color AS label_color_2, ls3.label_color AS label_color_3, ls1.label_text AS label_text_1, ls2.label_text AS label_text_2, ls3.label_text AS label_text_3, second_icon_flag, third_icon_flag," +
            " ls1.label_border_color AS label_border_color_1,ls2.label_border_color AS label_border_color_2,ls3.label_border_color AS label_border_color_3 ,second_icon_flag,third_icon_flag,img_url_1,img_url_2,img_url_3 "+
            " FROM enterprise_info ei LEFT JOIN label_style ls1 ON ei.first_Icon_flag = ls1.id LEFT JOIN label_style ls2 ON ei.second_icon_flag = ls2.id LEFT JOIN label_style ls3 ON ei.third_icon_flag = ls3.id " +
            " WHERE <if test =\"  name != null and name != '' \"> enterprise_name LIKE concat(#{name},'%') AND </if>" +
            " ei.delete_flag != 1 LIMIT #{page},#{count} </script>")
    List<EnterpriseInfoDTO> selectCompanyByName(@Param("name") String name,@Param("page") int page,@Param("count") int count);

    @Insert({"<script>",
            "INSERT INTO enterprise_info (enterprise_name,create_date) VALUE (#{enterpriseName},NOW())",
            "</script>"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertNewCompany(EnterpriseInfo enterpriseInfo);

    @Insert("INSERT INTO user_enterprise_rel (user_id,enterprise_id) VALUE (#{userId},#{companyId})")
    void insertNewCompnayRel(@Param("companyId") long companyId,@Param("userId") long userId);

}
