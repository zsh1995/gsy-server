package com.gsy.base.web.services.impl;

import com.gsy.base.web.dao.UploadScoreDAO;
import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.services.UploadScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/6/18.
 */
@Service
public class UploadScoreServiceImpl implements UploadScoreService {
    @Autowired
    UploadScoreDAO uploadScoreDAO;
    @Override
    public boolean uploadScore(String openId, PracticeRecordDTO practiceRecordDTO) {
        UserInfoDTO dto = uploadScoreDAO.findIdByOpenId(openId);
        practiceRecordDTO.setUserId(dto.getId());
        uploadScoreDAO.insertNewScore(practiceRecordDTO);
        return true;
    }
}
