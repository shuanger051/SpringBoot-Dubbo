package com.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.dubbo.api.SchedulerService;
import com.dubbo.api.entity.SchedulerEntity;
import com.dubbo.api.entity.SchedulerQueryEntity;
import com.dubbo.provider.dao.SchedulerMapper;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Service
@Component
public class SchedulerServiceImpl implements SchedulerService {

    private static Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Resource
    private Scheduler scheduler;

    @Resource
    private SchedulerMapper schedulerMapper;

    /**
     * 修改作业的 cron 触发器规则，并重新注册。
     * @param schedulerEntity
     * @return
     */
    @Override
    public Date rescheduleJob(SchedulerEntity schedulerEntity) throws SchedulerException {
        //触发器参数
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerEntity.getTriggerName(), schedulerEntity.getTriggerGroup());
        // scheduler.getTrigger(TriggerKey triggerKey)：从调度器中获取指定的触发器
        //修改任务的触发器时，触发器必须存在再修改.
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            logger.warn("根据 {} -> {} 未查到对应触发器..", triggerKey.getGroup(), triggerKey.getName());
            return null;
        }
        Trigger triggerNew = this.getTrigger(schedulerEntity, null);
        /**
         * rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)：重新注册作业
         *      先根据 triggerKey 删除指定的触发器，然后存储新触发器(newTrigger)，并关联相同的作业.
         * 一个触发器只能关联一个 Job，而 一个 Job 可以关联多个触发器.
         */
        Date nextDate = scheduler.rescheduleJob(triggerKey, triggerNew);
        logger.info("重新绑定作业触发器.{} -> {}." + triggerKey.getGroup(), triggerKey.getName());
        return nextDate;
    }

    /**
     * 注册 job 与 触发器。
     * @param schedulerEntity
     * @throws SchedulerException
     */
    public void scheduleJobOrTrigger(SchedulerEntity schedulerEntity) throws SchedulerException, ClassNotFoundException {
        //1)注册 job 作业
        String job_class_name = schedulerEntity.getJobClassName();
        JobDetail jobDetail = null;
        if (StringUtils.isNotBlank(job_class_name)) {
            jobDetail = this.getJobDetail(schedulerEntity);
            //往调度器中添加作业.
            scheduler.addJob(jobDetail, true);
            logger.info("往调度器中添加作业 {}," + jobDetail.getKey());
        }
        //2）注册触发器，触发器必须关联已经存在的作业
        String job_name = schedulerEntity.getJobName();
        String job_group = schedulerEntity.getJobGroup();
        if (jobDetail == null && StringUtils.isNotBlank(job_group) && StringUtils.isNotBlank(job_name)) {
            jobDetail = scheduler.getJobDetail(JobKey.jobKey(job_name, job_group));
        }
        String cron_expression = schedulerEntity.getCronExpression();
        Trigger trigger = null;
        if (jobDetail != null && StringUtils.isNotBlank(cron_expression)) {
            trigger = this.getTrigger(schedulerEntity, JobKey.jobKey(job_name, job_group));
        }
        if (trigger == null) {
            return;
        }
        //注册触发器。如果触发器不存在，则新增，否则修改
        boolean checkExists = scheduler.checkExists(trigger.getKey());
        if (checkExists) {
            //rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)：更新指定的触发器.
            scheduler.rescheduleJob(trigger.getKey(), trigger);
        } else {
            //scheduleJob(Trigger trigger)：注册触发器，如果触发器已经存在，则报错.
            scheduler.scheduleJob(trigger);
        }
    }

    /**
     * 删除作业。作业一旦删除，数据库中就不存在了。
     * @param queryEntity
     * @throws SchedulerException
     */
    @Override
    public void deleteJob(SchedulerQueryEntity queryEntity) throws SchedulerException {
        //如果 JobKey 指定的作业不存在，则 deleteJob(JobKey jobKey) 无实质性操作，不会抛异常.
        //删除作业的同时，关联的触发器也会一起被删除.
        JobKey jobKey = new JobKey(queryEntity.getJobName(),queryEntity.getJobGroup());
        scheduler.deleteJob(jobKey);
        logger.info("删除作业 {} -> {}" + jobKey.getGroup(), jobKey.getName());
    }

    /**
     * 删除多个作业
     * @param jobKeyList
     * @throws SchedulerException
     */
    public void deleteJobList(List<JobKey> jobKeyList) throws SchedulerException {
        //如果 JobKey 指定的作业不存在，则 deleteJob(JobKey jobKey) 无实质性操作，不会抛异常.
        //删除作业的同时，关联的触发器也会一起被删除.
        scheduler.deleteJobs(jobKeyList);
        logger.info("删除作业 {}", jobKeyList);
    }

    /**
     * 清除/删除所有计划数据，包括所有的 Job，所有的 Trigger，所有的 日历。
     * @throws SchedulerException
     */
    @Override
    public void clear() throws SchedulerException {
        scheduler.clear();
        logger.info("清除/删除所有计划数据，包括所有的 Job，所有的 Trigger，所有的 日历。");
    }

    /**
     * 暂停作业。
     * @param queryEntity
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(SchedulerQueryEntity queryEntity) throws SchedulerException {
        JobKey jobKey = new JobKey(queryEntity.getJobName(),queryEntity.getJobGroup());
        scheduler.pauseJob(jobKey);
        logger.info("暂停作业 {} -> {}" + jobKey.getGroup(), jobKey.getName());
    }

    /**
     * 暂停所有作业。
     * @throws SchedulerException
     */
    @Override
    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
        logger.info("暂停所有作业.");
    }

    /**
     * 恢复指定作业继续运行。
     * @param queryEntity
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(SchedulerQueryEntity queryEntity) throws SchedulerException {
        //resumeTrigger(TriggerKey triggerKey)：恢复指定触发器
        //resumeJobs(GroupMatcher<JobKey> matcher)：恢复匹配的整个组下的所有作业.
        JobKey jobKey = new JobKey(queryEntity.getJobName(),queryEntity.getJobGroup());
        scheduler.resumeJob(jobKey);
        logger.info("恢复指定作业 {}", jobKey);
    }

    /**
     * 恢复所有作业。
     * @throws SchedulerException
     */
    @Override
    public void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
        logger.info("恢复所有作业.");
    }

    /**
     * 停止/关闭 quartz 调度程序，关闭了整个调度的线程池，意味者所有作业都不会继续执行。
     * @throws SchedulerException
     */
    @Override
    public void shutdown() throws SchedulerException {
        scheduler.shutdown(true);
    }

    /**
     * 查询已经注册成功的任务列表
     * @param queryEntity
     * @return
     */
    @Override
    public List<SchedulerEntity> findSchedulers(SchedulerQueryEntity queryEntity) {
        SchedulerQueryEntity query = new SchedulerQueryEntity();
        query.setPageNum((queryEntity.getPageNum()-1)*queryEntity.getPageSize());
        query.setPageSize(queryEntity.getPageNum()*queryEntity.getPageSize());
        return schedulerMapper.findSchedulers(query);
    }

    /**
     * 注册任务
     * @param schedulerEntity
     */
    @Override
    public void addSchedulers(SchedulerEntity schedulerEntity) throws Exception{
        this.schedulerJob(schedulerEntity);
    }

    private void schedulerJob(SchedulerEntity schedulerEntity) throws Exception{
        JobDetail jobDetail = this.getJobDetail(schedulerEntity);
        Trigger trigger = this.getTrigger(schedulerEntity, null);
        //scheduleJob(JobDetail jobDetail, Trigger trigger):作业注册并启动。注意同一个组下面的任务详情或者触发器名称必须唯一，否则重复注册时会报错，已经存在.
        //scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace)
        // replace=true，表示如果存储相同的 Job 或者 Trigger ，则替换它们
        //因为全局配置文件中配置了 spring.quartz.uto-startup=true，所以不再需要手动启动：scheduler.start()
        Set<Trigger> triggerSet = new HashSet<>();
        triggerSet.add(trigger);
        scheduler.scheduleJob(jobDetail, triggerSet, true);
        logger.info("注册并启动作业:{}", schedulerEntity);
    }

    /**
     * 内部方法：处理 Trigger
     *
     * @param schedulerEntity
     * @return
     */
    private Trigger getTrigger(SchedulerEntity schedulerEntity, JobKey jobKey) {
        //触发器参数
        //schedulerEntity 中 job_data 属性值必须设置为 json 字符串格式，所以这里转为 JobDataMap 对象.
        JobDataMap triggerDataMap = new JobDataMap();
        Map<String, Object> triggerData = (Map) JSON.parse(schedulerEntity.getTriggerData());
        if (triggerData != null && triggerData.size() > 0) {
            triggerDataMap.putAll(triggerData);
        }
        //如果触发器名称为空，则使用 UUID 随机生成. group 为null时，会默认为 default.
        if (StringUtils.isBlank(schedulerEntity.getTriggerName())) {
            schedulerEntity.setTriggerName(UUID.randomUUID().toString());
        }
        //过期执行策略采用：MISFIRE_INSTRUCTION_DO_NOTHING
        //forJob：为触发器关联作业. 一个触发器只能关联一个作业.
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(schedulerEntity.getTriggerName(), schedulerEntity.getTriggerGroup());
        triggerBuilder.withDescription(schedulerEntity.getTriggerDesc());
        triggerBuilder.usingJobData(triggerDataMap);
        if (jobKey != null && jobKey.getName() != null) {
            triggerBuilder.forJob(jobKey);
        }
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(schedulerEntity.getCronExpression())
                .withMisfireHandlingInstructionDoNothing());
        return triggerBuilder.build();
    }

    /**
     * 内部方法：处理 JobDetail
     * storeDurably(boolean jobDurability)：指示 job 是否是持久性的。如果 job 是非持久的，当没有活跃的 trigger 与之关联时，就会被自动地从 scheduler 中删除。即非持久的 job 的生命期是由 trigger 的存在与否决定的.
     * requestRecovery(boolean jobShouldRecover) :指示  job 遇到故障重启后，是否是可恢复的。如果 job 是可恢复的，在其执行的时候，如果 scheduler 发生硬关闭（hard shutdown)（比如运行的进程崩溃了，或者关机了），则当 scheduler 重启时，该 job 会被重新执行。
     *
     * @param schedulerEntity
     * @return
     * @throws ClassNotFoundException
     */
    private JobDetail getJobDetail(SchedulerEntity schedulerEntity) throws ClassNotFoundException {
        //如果任务名称为空，则使用 UUID 随机生成.
        if (StringUtils.isBlank(schedulerEntity.getJobName())) {
            schedulerEntity.setJobName(UUID.randomUUID().toString());
        }
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(schedulerEntity.getJobClassName());
        //作业参数
        JobDataMap jobDataMap = new JobDataMap();
        Map<String, Object> jobData = (Map<String,Object>)JSON.parse(schedulerEntity.getJobData());
        if (jobData != null && jobData.size() > 0) {
            jobDataMap.putAll(jobData);
        }
        //设置任务详情.
        return JobBuilder.newJob(jobClass)
                .withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup())
                .withDescription(schedulerEntity.getJobDesc())
                .usingJobData(jobDataMap)
                .storeDurably(true)
                .requestRecovery(true)
                .build();
    }


}
