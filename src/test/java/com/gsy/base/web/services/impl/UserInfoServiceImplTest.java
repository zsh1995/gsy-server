package com.gsy.base.web.services.impl;

import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.web.services.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mrzsh on 2018/3/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {
    @Test
    public void updateUserInvitor() throws Exception {
        try{
            userInfoService.updateUserInvitor(161L,"o5TL-0FErAiYMqeXGCs0T1N2KlP4");
        } catch (Throwable e){
            Assert.assertTrue(e instanceof NoPermissionException);
        }

    }

    @Autowired
    UserInfoService userInfoService;
    @Test
    public void getPurchExamRecord() throws Exception {
        List list = userInfoService.getPurchExamRecord("o5TL-0FErAiYMqeXGCs0T1N2KlP4");
        System.out.print(list);
    }

}