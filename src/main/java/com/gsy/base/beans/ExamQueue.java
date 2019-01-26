package com.gsy.base.beans;


import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.dto.RedisItemDTO;
import com.gsy.base.web.entity.Question;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by mrzsh on 2018/4/16.
 */
@Lazy
@Component
public class ExamQueue {

    @Autowired
    RedisBean redisBean;
    @Autowired
    BeanFactory beanFactory;


    private static final Logger logger = Logger.getLogger(ExamQueue.class);

    private static final int TIMMER_COUNT_MINUTES = 120;

    @Autowired
    public ExamQueue(RedisBean redisBean,BeanFactory beanFactory){
        this.redisBean = redisBean;
        this.beanFactory = beanFactory;
        logger.info("ExamQueue first launch");
        this.init();
        this.load();
        logger.info("ExamQueue: finished load old data");
    }


    ExecutorService executor = Executors.newCachedThreadPool();

    private Thread daemonThread;


    public void load(){
        Set set = redisBean.getAll();
        if(set.isEmpty()){
            logger.info("old is Empty");
            return;
        }
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String keyName = it.next();
            RedisItemDTO<List<Question>> list = (RedisItemDTO<List<Question>>) redisBean.get(keyName);
            long elapsedTime = System.nanoTime() - list.getCreateTime();
            long settingTime = TimeUnit.NANOSECONDS.convert(TIMMER_COUNT_MINUTES,TimeUnit.MINUTES);
            if( elapsedTime >= settingTime){
                redisBean.delete(keyName);
            } else{
                String[] args = keyName.split("-");
                ExamTask task = (ExamTask) beanFactory.getBean("examTask",Long.valueOf(args[0]),Integer.valueOf(args[1]));
                put(settingTime - elapsedTime ,task,TimeUnit.NANOSECONDS);
            }
        }
    }

    public void init(){

        daemonThread = new Thread(()->{
           try {
               execute();
           }catch (InterruptedException e){
               e.printStackTrace();
               logger.error(e);
           }
        });
        daemonThread.start();
    }
    private DelayQueue<DelayItem<Runnable>> item = new DelayQueue<>();

    private void execute() throws InterruptedException{
        while(true){
            Map<Thread,StackTraceElement[]> map = Thread.getAllStackTraces();
            DelayItem<Runnable> t = item.take();
            if(t != null){
                Runnable task = t.getTask();
                if( task == null) continue;
                executor.execute(task);
            }
        }
    }

    public void put(long time, Runnable task, TimeUnit timeUnit){
        long nanoTime = TimeUnit.NANOSECONDS.convert(time,timeUnit);
        DelayItem<Runnable> k = new DelayItem<>(nanoTime,task);
        item.put(k);
    }

    public void putExam(long uid,int star){
        logger.info(MessageFormat.format("exam enqueue, uid = {0},start = {1}", uid, star));
        ExamTask examTask = (ExamTask) beanFactory.getBean("examTask",uid,star);
        put(TIMMER_COUNT_MINUTES,examTask,TimeUnit.MINUTES);
    }

    public boolean endTask(DelayItem<Runnable> task){
        return item.remove(task);
    }

    public static void main(String[] args){
        //ExamQueue examQueue = ExamQueue.getInstance();
        //examQueue.init();
        Random random = new Random();/*
        for(int i = 0;i < 50;i++){
            int randomNum = random.nextInt(200);
            System.out.println(randomNum);
            ExamTask task = new ExamTask();
            task.a = randomNum;
            examQueue.put(randomNum,task,TimeUnit.SECONDS);
        }
        */
    }



}
