package com.gsy.base.web.dao;

import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mrzsh on 2018/2/7.
 */
@Mapper
public interface UploadScoreDAO {

    @Select("SELECT id FROM wechat_userinfo WHERE openid = #{openId}")
    UserInfoDTO findIdByOpenId(String openId);

    @Insert("INSERT INTO practice_record(user_id,question_group,score,stars,create_date) " +
            "VALUE( #{userId},#{questionGroup},#{score},#{stars},NOW()) " +
            "ON DUPLICATE KEY " +
            "UPDATE user_id= #{userId},question_group=#{questionGroup},score=#{score},stars=#{stars},create_date=NOW()")
    int insertNewScore(PracticeRecordDTO dto);

    @Select("SELECT * FROM practice_record WHERE user_id = #{id} AND stars = #{stars}")
    List<PracticeRecordDTO> getScore(@Param("id") Integer id,@Param("stars") Integer stars);
}
