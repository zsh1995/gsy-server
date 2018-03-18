package com.gsy.base.web.dao;

import com.gsy.base.web.dto.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mrzsh on 2018/2/7.
 */
@Mapper
public interface QuestionDAO {

    @Select("SELECT wqw.id as questionId, wqw.type, wqw.tip as tips, wqw.analysis as analyse, wqw.question_content as content, pr1.id AS isPurchAnalyse FROM wechat_questions_work wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN purch_rel pr2 ON( pr2.prop_value = 1 AND pr2.prop_name = 'star' AND pr2.user_openId = #{openId}) LEFT JOIN purch_rel pr1 ON ( pr1.prop_value = wqw.id AND pr1.product_id = pr2.product_id AND pr1.prop_name = 'questionId' AND pr1.user_openId = #{openId} ) WHERE pq.practice_class = 3 AND pq.group_id = #{groupId} GROUP BY questionId")
    List<QuestionDTO> getQuestionWork(@Param("openId") String openId,@Param("groupId") Integer groupId);

    @Select("SELECT wqw.id as questionId, wqw.type, wqw.tip as tips, wqw.analysis as analyse, wqw.question_content as content, pr1.id AS isPurchAnalyse FROM wechat_questions_love wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN purch_rel pr2 ON( pr2.prop_value = 1 AND pr2.prop_name = 'star' AND pr2.user_openId = #{openId}) LEFT JOIN purch_rel pr1 ON ( pr1.prop_value = wqw.id AND pr1.product_id = pr2.product_id AND pr1.prop_name = 'questionId' AND pr1.user_openId = #{openId} ) WHERE pq.practice_class = 1 AND pq.group_id = #{groupId} GROUP BY questionId")
    List<QuestionDTO> getQuestionLove(@Param("openId") String openId,@Param("groupId") Integer groupId);

    @Select("SELECT wqw.id as questionId, wqw.type, wqw.tip as tips, wqw.analysis as analyse, wqw.question_content as content, pr1.id AS isPurchAnalyse FROM wechat_questions_social wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN purch_rel pr2 ON( pr2.prop_value = 1 AND pr2.prop_name = 'star' AND pr2.user_openId = #{openId}) LEFT JOIN purch_rel pr1 ON ( pr1.prop_value = wqw.id AND pr1.product_id = pr2.product_id AND pr1.prop_name = 'questionId' AND pr1.user_openId = #{openId} ) WHERE pq.practice_class = 2 AND pq.group_id = #{groupId} GROUP BY questionId")
    List<QuestionDTO> getQuestionSocial(@Param("openId") String openId,@Param("groupId") Integer groupId);

    @Select("SELECT analysis FROM wechat_questions_love WHERE id = #{id}")
    String getAnalyseLove(int id);

    @Select("SELECT analysis FROM wechat_questions_work WHERE id = #{id}")
    String getAnalyseWork(int id);

    @Select("SELECT analysis FROM wechat_questions_social WHERE id = #{id}")
    String getAnalyseSocial(int id);

}
