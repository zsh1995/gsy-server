package com.gsy.base.web.controller.ajaxcontroller;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.web.dto.PageHelperDTO;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mrzsh on 2018/2/6.
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    /**
     * 获取公司列表
     */
    @RequestMapping("/allList")
    public ResultBean getAllList(UserInfoEntity userInfoEntity,
                                 @RequestParam int page,
                                 @RequestParam int count,
                                 @RequestParam(defaultValue = "") String name){
        PageHelperDTO pageHelper = new PageHelperDTO();
        pageHelper.setList(companyService.getAllCompany(name,page,count));
        pageHelper.setAllCount(companyService.getCompanyCount(name));
        return new ResultBean(pageHelper);
    }

}
