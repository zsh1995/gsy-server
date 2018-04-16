package com.gsy.base.beans;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mrzsh on 2018/4/16.
 */
public class DelayItem<T extends Runnable> implements Delayed {

    private final long endTime;

    private final T task;

    private static final AtomicLong atomic = new AtomicLong(0);

    private final long n;

    public DelayItem(long timeout, T task) {
        this.endTime = timeout + System.nanoTime();
        this.task = task;
        this.n = atomic.getAndIncrement();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.endTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == this) return 0;
        if(o instanceof DelayItem){
            DelayItem<?> x = (DelayItem<?>) o;
            long diff = endTime - x.endTime;
            if(diff < 0)       return -1;
            else if(diff > 0) return  1;
            else if(n < x.n)  return -1;
            else               return 1;
        }
        long delay = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (delay == 0) ? 0 : ((delay < 0) ? -1 : 1);
    }
    public T getTask(){
        return this.task;
    }
    @Override
    public int hashCode(){
        return task.hashCode();
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof  DelayItem){
            return object.hashCode() == hashCode();
        }
        return false;
    }

}
