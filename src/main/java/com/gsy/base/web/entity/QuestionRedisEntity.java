package com.gsy.base.web.entity;

import com.gsy.base.common.exam.ExamHelper;

/**
 * Created by mrzsh on 2018/1/29.
 */
public class QuestionRedisEntity extends BaseEntity {

    private ExamHelper.ExamType type;
    private long id;

    public ExamHelper.ExamType getType() {
        return type;
    }

    public void setType(ExamHelper.ExamType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
