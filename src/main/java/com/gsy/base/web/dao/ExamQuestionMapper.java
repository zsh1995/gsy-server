package com.gsy.base.web.dao;

import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.entity.ExamQuestions;
import com.gsy.base.web.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by mrzsh on 2018/1/28.
 */
@Mapper
public interface ExamQuestionMapper {

    @Select("<script>" +
            "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content " +
            "FROM <if test=\" star ==1\">wechat_questions_love </if> " +
            "<if test=\" star ==2\">wechat_questions_social </if>" +
            "<if test=\" star ==3\">wechat_questions_work </if>" +
            "wqw ORDER BY  RAND() LIMIT 6</script>")
    @Results({
            @Result(property = "id",column = "id",javaType = Long.class),
            @Result(property = "type",column = "type",javaType = ExamHelper.ExamType.class),
            @Result(property = "tip",column = "tip",javaType = String.class),
            @Result(property = "analysis",column = "analysis",javaType = String.class),
            @Result(property = "content",column = "question_content",javaType = String.class),
    })
    List<Question> getExamQuestions(@Param(value = "star") int star);
}
