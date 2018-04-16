package com.gsy.base.web.dao;

import com.gsy.base.web.dto.UserInfoDTO;
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
public class UserDAOTest {

    @Autowired
    UserDAO userDAO;
    @Test
    public void updateUserInfo() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setOpenId("o5TL-0FErAiYMqeXGCs0T1N2KlP4");
        userInfoDTO.setWantedCompany1("1");
        userInfoDTO.setUserChannel(1);
        userDAO.updateUserInfo(userInfoDTO);
    }

}