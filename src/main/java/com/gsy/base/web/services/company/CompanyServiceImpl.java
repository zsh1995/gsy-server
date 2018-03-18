package com.gsy.base.web.services.company;


import com.gsy.base.common.ApiMethod;
import com.gsy.base.web.dao.CompanyDAO;
import com.gsy.base.web.dto.EnterpriseInfoDTO;
import com.gsy.base.web.entity.merge.EnterpriseInfo;
import com.gsy.base.web.entity.merge.LabelStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzsh on 2017/11/4.
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyDAO companyDAO;

    @Override
    public boolean isExited(String name) {
        return companyDAO.selectCompanyCount(name) > 0 ? true:false;
    }

    @Override
    public boolean addNewCompany(EnterpriseInfo enterpriseInfo, long userId){
        if(ApiMethod.isEmpty(enterpriseInfo.getEnterpriseName())) return false;
        if(isExited(enterpriseInfo.getEnterpriseName())) return false;
        companyDAO.insertNewCompany(enterpriseInfo);
        companyDAO.insertNewCompnayRel(enterpriseInfo.getId(),userId);
        return true;
    }

    @Override
    public List<EnterpriseInfoDTO> getAllCompany(String name, int page, int count) {
        List<EnterpriseInfoDTO> dataList;
        dataList =companyDAO.selectCompanyByName(name,page,count);
        for(EnterpriseInfoDTO enterpriseInfo : dataList){
            List<LabelStyle> tempArray = new ArrayList<>();
            LabelStyle labelStyle1 = new LabelStyle();
            LabelStyle labelStyle2 = new LabelStyle();
            LabelStyle labelStyle3 = new LabelStyle();
            labelStyle1.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor1());
            labelStyle2.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor2());
            labelStyle3.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor3());
            labelStyle1.setLabelColor(enterpriseInfo.getLabelColor1());
            labelStyle2.setLabelColor(enterpriseInfo.getLabelColor2());
            labelStyle3.setLabelColor(enterpriseInfo.getLabelColor3());
            labelStyle1.setLabelText(enterpriseInfo.getLabelText1());
            labelStyle2.setLabelText(enterpriseInfo.getLabelText2());
            labelStyle3.setLabelText(enterpriseInfo.getLabelText3());
            labelStyle1.setLabelBorderColor(enterpriseInfo.getLabelBorderColor1());
            labelStyle2.setLabelBorderColor(enterpriseInfo.getLabelBorderColor2());
            labelStyle3.setLabelBorderColor(enterpriseInfo.getLabelBorderColor3());
            labelStyle1.setImgUrl(enterpriseInfo.getImgUrl1());
            labelStyle2.setImgUrl(enterpriseInfo.getImgUrl2());
            labelStyle3.setImgUrl(enterpriseInfo.getImgUrl3());
            tempArray.add(labelStyle1);
            tempArray.add(labelStyle2);
            tempArray.add(labelStyle3);
            enterpriseInfo.setIconArray(tempArray);
        }
        return dataList;
    }

    @Override
    public int getCompanyCount(String name) {
        return companyDAO.selectCompanyCount(name);
    }

    @Override
    public List<EnterpriseInfoDTO> searchCompany(String name, int page, int count) {
        List<EnterpriseInfoDTO> dataList =companyDAO.selectCompanyByName(name,page,count);
        for(EnterpriseInfoDTO enterpriseInfo : dataList){
            List<LabelStyle> tempArray = new ArrayList<>();
            LabelStyle labelStyle1 = new LabelStyle();
            LabelStyle labelStyle2 = new LabelStyle();
            LabelStyle labelStyle3 = new LabelStyle();
            labelStyle1.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor1());
            labelStyle2.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor2());
            labelStyle3.setLabelBackgroundcolor(enterpriseInfo.getLabelBackgroundcolor3());
            labelStyle1.setLabelColor(enterpriseInfo.getLabelColor1());
            labelStyle2.setLabelColor(enterpriseInfo.getLabelColor2());
            labelStyle3.setLabelColor(enterpriseInfo.getLabelColor3());
            labelStyle1.setLabelText(enterpriseInfo.getLabelText1());
            labelStyle2.setLabelText(enterpriseInfo.getLabelText2());
            labelStyle3.setLabelText(enterpriseInfo.getLabelText3());
            labelStyle1.setImgUrl(enterpriseInfo.getImgUrl1());
            labelStyle2.setImgUrl(enterpriseInfo.getImgUrl2());
            labelStyle3.setImgUrl(enterpriseInfo.getImgUrl3());
            tempArray.add(labelStyle1);
            tempArray.add(labelStyle2);
            tempArray.add(labelStyle3);
            enterpriseInfo.setIconArray(tempArray);
        }
        return dataList;
    }

    public static void main(String[] args){
        CompanyService companyService = new CompanyServiceImpl();
        List enterpriseInfo = companyService.getAllCompany("华",0,10);
        System.out.print(enterpriseInfo);

    }

}
