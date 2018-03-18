package com.gsy.base.web.services.company;

import com.gsy.base.web.entity.merge.EnterpriseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by mrzsh on 2018/2/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CompanyServiceTest {
    @Autowired
    CompanyService companyService;

    @Test
    public void isExited() throws Exception {
    }

    @Test
    public void addNewCompany() throws Exception {
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseName("法拉利");
        companyService.addNewCompany(enterpriseInfo,9112);
    }

    @Test
    public void getAllCompany() throws Exception {
        System.out.println(companyService.getAllCompany("",0,10));
    }

    @Test
    public void getCompanyCount() throws Exception {
        companyService.getCompanyCount("华");
    }

    @Test
    public void searchCompany() throws Exception {
        System.out.println(companyService.searchCompany("华",0,10));
    }

}