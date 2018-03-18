package com.gsy.base.web.dao;

import com.gsy.base.web.dto.AppendEntity;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.merge.WechatUserRight;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by mrzsh on 2018/2/7.
 */
@Mapper
public interface UserDAO {


    @Insert("INSERT INTO wechat_userinfo(wechat_nick,openId,avatar_url,create_time) VALUE( #{nickName} , #{openId} , #{avatarUrl} ,NOW()) ON DUPLICATE KEY UPDATE wechat_nick=  #{nickName} ,openId= #{openId} ,avatar_url= #{avatarUrl} ,last_use=NOW()")
    int insertNewLoginUser(UserInfoDTO userinfo);


    @Select("SELECT wu1.user_channel,wu1.id,wu1.student_name,wu1.school,wu1.major,wu1.phone_number,wu1.post,wu1.type,wu1.city,wu1.gender,wu1.wanted_company1,wu1.wanted_company2,wu1.wanted_company3,wu1.wanted_company4,wu1.wanted_company5,IF(wu2.student_name = '' OR wu2.student_name  = NULL,wu2.wechat_nick,wu2.student_name) as invitor, wu1.emailAddr FROM wechat_userinfo wu1 LEFT JOIN wechat_userinfo wu2 ON wu2.id = wu1.invitor WHERE wu1.openid =  #{openId}  AND wu1.delete_flag = 0")
    UserInfoDTO getUserInfo(String openId);

    @Select("SELECT openId,avatar_url ,IFNULL(student_name,wechat_nick) as nick_name FROM wechat_userinfo WHERE id = #{id}")
    UserInfoDTO selectInvitor(Long id);

    @Update("UPDATE wechat_userinfo SET invitor = #{id} WHERE openId = #{openId}")
    int updateInvitor(@Param("id") Long id,@Param("openId") String openId);

    @Update("UPDATE wechat_userinfo\n" +
            "SET student_name = #{studentName}  ,school = #{school}  ,major =  #{major}  ,phone_number =  #{phoneNumber}  ,post=\n" +
            " #{post}  ,type=  #{type}  ,city=  #{city}  ,gender=  #{gender}  ,company=  #{company} , emailAddr= #{emailAddr}  ,\n" +
            "user_channel= #{userChannel}\n" +
            "WHERE\n" +
            " openId = #{openId}")
    int updateUserInfo(UserInfoDTO userInfo);

    @Insert("INSERT INTO wechat_user_right ( openId,\n" +
            "        type,\n" +
            "        star,\n" +
            "        remain_times,\n" +
            "        create_time\n" +
            ") SELECT\n" +
            "         #{openId}  ,  #{type}  ,  #{star}  , dt.dict_value,\n" +
            "        NOW()\n" +
            "FROM\n" +
            "        dict_table dt\n" +
            "WHERE\n" +
            "        dt.dict_name = 'TIMES_PER_PURCH'")
    int insertNewUserRight(WechatUserRight userRight);

    @Insert("INSERT INTO wechat_user_right (openId, type, star, questionId, remain_times , create_time) (SELECT openId, per.type, per.purch_star, per.purch_question_id, dt.dict_value , NOW() FROM purch_exam_record per LEFT JOIN dict_table dt ON dt.dict_name = 'TIMES_PER_PURCH' WHERE per.trade_no = #{tradeNo} ) ON DUPLICATE KEY UPDATE remain_times = dt.dict_value,passExam = 0")
    int insertUserRightByPurchedInfo(@Param("openId") String openId,@Param("tradeNo") String tradeNo);


    @Update("UPDATE wechat_user_rightSET remain_times = #{remainTimes}\n" +
            "WHERE\n" +
            "        openId = #{openId}   AND type =  #{type}   AND star =  #{star}")
    int updateUserRightRemainTimes(WechatUserRight userRight);

    @Update("UPDATE purch_rel SET prop_value =  #{newTimes}   WHERE user_openId =  #{openId}   AND product_id =( SELECT product_id FROM product WHERE product_name = 'exam' AND prop_name = 'star' AND prop_value =  #{star}  ) AND prop_name = 'avaliable_times'")
    int updateExamTimes(@Param("openId") String openId,@Param("star") int star,@Param("newTimes") int newTimes);


    @Insert("INSERT INTO exam_record_table(openId, star, pass_times,create_date) VALUE ( #{openId}  , #{star}  ,1,NOW()) ON DUPLICATE KEY UPDATE pass_times =  #{passTimes}  ,pass_flag =  #{passed}")
    int updateExamPassTimes(@Param("openId") String openId,@Param("star") int star,@Param("passTimes") int passTimes,@Param("passed")int passed);

    @Select("SELECT pass_times FROM exam_record_table WHERE openId = #{openId} AND star = #{star}")
    int findExamPassTimes(@Param("openId") String openId,@Param("star") int star);

    @Select("SELECT pr.id FROM purch_rel pr LEFT JOIN product prd ON pr.product_id = prd.product_id WHERE 1 = 1 AND prd.product_name = 'returnable' AND pr.user_openId = #{openId} AND pr.delete_flag != 1 LIMIT 1")
    long findReturnableById(String openId);


    @Select("SELECT remain_times FROM wechat_user_right WHERE openId =  #{openId}   AND type =  #{type}   AND star =  #{star}")
    int selecteUserRightRemainTimes(WechatUserRight userRight);

    @Select("SELECT IFNULL(invitedWU.student_name, invitedWU.wechat_nick) AS `nickName`, invitedWU.avatar_url ,invitedWU.create_time FROM wechat_userinfo invitedWU LEFT JOIN wechat_userinfo invitorWU ON invitorWU.id = invitedWU.invitor WHERE invitorWU.openId = #{openId}")
    List<UserInfoDTO> queryAllInvite(String openId);

    @Select("SELECT count(invitedWU.id) as count FROM wechat_userinfo invitedWU LEFT JOIN wechat_userinfo invitorWU ON invitorWU.id = invitedWU.invitor WHERE invitorWU.openId = #{openId}")
    int selectInvitedCount(String openId);


    @Select("SELECT * FROM append_auth WHERE openId = #{openId} AND delete_flag != 1")
    AppendEntity findAppend(String openId);

    @Select("SELECT wu2.avatar_url, IF(wu2.student_name = '' OR wu2.student_name  = NULL,wu2.wechat_nick,wu2.student_name) as name FROM wechat_userinfo wu1 LEFT JOIN wechat_userinfo wu2 ON wu2.id = wu1.invitor WHERE wu1.openId = #{openId}")
    Map getMyInvitor(String openId);

    @Select("SELECT id FROM wechat_user_right WHERE openId =  #{openId}   AND type =  #{type}   AND questionId =  #{questionId}   AND star =  #{star}")
    long selecteUserRightAnalyse(WechatUserRight userRight);

}
