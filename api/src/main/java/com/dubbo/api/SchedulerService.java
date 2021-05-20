package com.dubbo.api;

import com.dubbo.api.entity.SchedulerEntity;
import com.dubbo.api.entity.SchedulerQueryEntity;
import org.quartz.SchedulerException;

import java.util.Date;
import java.util.List;

public interface SchedulerService {

    //查询任务列表
    public List<SchedulerEntity> findSchedulers(SchedulerQueryEntity schedulerQueryEntity) throws Exception;
    //注册任务
    public void addSchedulers(SchedulerEntity schedulerEntity) throws Exception;
    //重新注册任务
    public Date rescheduleJob(SchedulerEntity schedulerEntity) throws SchedulerException;
    //删除指定任务
    public void deleteJob(SchedulerQueryEntity queryEntity) throws SchedulerException;
    //清空所有任务
    public void clear() throws SchedulerException;
    //暂停指定任务
    public void pauseJob(SchedulerQueryEntity queryEntity) throws SchedulerException;
    //暂停所有任务
    public void pauseAll() throws SchedulerException;
    //唤醒指定任务
    public void resumeJob(SchedulerQueryEntity queryEntity) throws SchedulerException;
    //唤醒全部任务
    public void resumeAll() throws SchedulerException;
    //关闭任务调度池，必须重启APP才能唤醒
    public void shutdown() throws SchedulerException;

}
