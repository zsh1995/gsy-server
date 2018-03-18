package com.gsy.base.web.dao;


import com.gsy.base.Application;
import com.gsy.base.web.services.ExamService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mrzsh on 2018/1/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamQuestionsTest {


    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Test
    public void test(){
        System.out.print(examQuestionMapper.getExamQuestions(1).get(0));
    }
    @Test
    public void test2(){
        Assert.assertEquals(16, 16);
        System.out.print("hello test");
    }
}
