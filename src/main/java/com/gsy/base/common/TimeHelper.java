package com.gsy.base.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mrzsh on 2018/1/29.
 */
public class TimeHelper {

    public static final long SECOND_MINITE = 60;
    public static final long MILISECOND_SECOND = 1000;

    public static long currentMs(){
        Date date = new Date();
        return date.getTime();
    }

    public static long addMinute(long old,long delta){
        return old+delta* SECOND_MINITE*MILISECOND_SECOND;
    }

}
