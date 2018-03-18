package com.gsy.base.web.controller;

import com.gsy.base.beans.RedisBean;
import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.entity.User;
import com.gsy.base.web.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mrzsh on 2018/1/27.
 */
@RestController
public class ExamCenterController {

    @Autowired
    RedisBean redisBean;

    @Autowired
    ExamService examService;

    @RequestMapping(value = "/checkExist",method = POST)
    @ResponseBody
    boolean checkExsist(@RequestParam(value = "uid",defaultValue = "0")long uid,
                        @RequestParam(value = "star",defaultValue = "1")int star){
        return examService.checkExamExist(uid, ExamHelper.ExamStar.build(star));
    }

    @RequestMapping(value = "/getQuestions",method = POST)
    @ResponseBody
    List getQuestions(@RequestParam(value = "uid",defaultValue = "0")long uid,
                      @RequestParam(value = "star",defaultValue = "1")int star){
        return examService.getQuestions(uid, ExamHelper.ExamStar.build(star));
    }
    @RequestMapping(value = "/calcScore",method=POST)
    @ResponseBody
    double calcScore(@RequestParam(value = "uid",defaultValue = "0")long uid,
                     @RequestParam(value = "star",defaultValue = "1")int star,
                     @RequestParam(value = "chooseList") List<Integer> chooseList) throws Exception {
        return examService.calcScore(uid,ExamHelper.ExamStar.build(star),chooseList);
    }

}
