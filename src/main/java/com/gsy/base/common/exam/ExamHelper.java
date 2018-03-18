package com.gsy.base.common.exam;

import com.gsy.base.web.entity.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrzsh on 2018/1/28.
 */
public class ExamHelper {

    public enum ExamType{
        Z,S,A,M
    }
    public enum ExamStar{
        one(1),two(2),three(3);
        private int code;
        ExamStar(int code){
            this.code = code;
        }
        public int getCode(){
            return this.code;
        }
        public static ExamStar build(int code){
            ExamStar examStar = one;
            switch (code){
                case 1:examStar = one;break;
                case 2:examStar = two;break;
                case 3:examStar = three;break;
            }
            return examStar;
        }
    }

    public static final double[] SCORE_Z = {10,7.6,1,5,3};

    public static final double[] SCORE_S = {3,5,1,7.6,10};

    public static final double[] SCORE_M = {1,1,10,1,1};

    public static final double[] SCORE_A = {9,9,9,9,9};

    public static double calculateScore(List<Question> questionList,List<Integer> ansList){
        double sum = 0;
        for(int cnt = 0 ; cnt < ansList.size();cnt++){
            ExamType type = questionList.get(cnt).getType();
            double score = getScoreByType(type,ansList.get(cnt));
            sum+=score;
        }
        return sum;
    }

    public static double getScoreByType(ExamType type,int choose){
        double score = 0;
        switch (type){
            case Z:
                score = SCORE_Z[choose];
                break;
            case S:
                score = SCORE_S[choose];
                break;
            case A:
                score = SCORE_A[choose];
                break;
            case M:
                score = SCORE_M[choose];
                break;
            default:break;
        }
        return score;
    }



}
