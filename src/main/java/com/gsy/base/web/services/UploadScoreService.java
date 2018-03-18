package com.gsy.base.web.services;


import com.gsy.base.web.dto.PracticeRecordDTO;

/**
 * Created by Administrator on 2017/6/18.
 */
public interface UploadScoreService {

    boolean uploadScore(String openId, PracticeRecordDTO dto);

}
