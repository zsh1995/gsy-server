package com.gsy.base.web.services.company;



import com.gsy.base.web.dto.EnterpriseInfoDTO;
import com.gsy.base.web.entity.merge.EnterpriseInfo;

import java.util.List;

/**
 * Created by mrzsh on 2017/11/4.
 */
public interface CompanyService {

    //是否存在该公司
    boolean isExited(String name);

    //新增公司
    boolean addNewCompany(EnterpriseInfo enterpriseInfo, long userId);

    //
    List<EnterpriseInfoDTO> getAllCompany(String name, int page, int count);

    //
    int getCompanyCount(String name);

    //
    List<EnterpriseInfoDTO> searchCompany(String name, int page, int count);


}
