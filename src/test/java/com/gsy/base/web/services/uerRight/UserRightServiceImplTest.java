package com.gsy.base.web.services.uerRight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by mrzsh on 2018/3/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRightServiceImplTest {
    @Autowired
    UserRightService userRightService;
    @Test
    public void sendGiftCoupon() throws Exception {
        userRightService.sendGiftCoupon("o5TL-0FErAiYMqeXGCs0T1N2KlP4");
    }

}