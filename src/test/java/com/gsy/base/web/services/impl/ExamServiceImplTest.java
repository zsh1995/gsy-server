package com.gsy.base.web.services.impl;

import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.services.ExamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mrzsh on 2018/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamServiceImplTest {

    @Autowired
    ExamService examService;
    @Test
    public void getQuestions() throws Exception {
        System.out.print(examService.getQuestions(123131, ExamHelper.ExamStar.one));
    }
    @Test
    public void calcScore() throws Exception{
        List chooseList = Arrays.asList(new Integer[]{4,4,4,4,4});
        System.out.print(examService.calcScore(123131, ExamHelper.ExamStar.one,chooseList));
    }


}
