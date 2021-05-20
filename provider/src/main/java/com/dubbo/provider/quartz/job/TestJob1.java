package com.dubbo.provider.quartz.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当任务的执行时间过长，而触发的时间间隔小于执行时间，则会导致同一个 JobDetail 实例被并发执行，如果不想让它并发执行，
 * 则加上 @DisallowConcurrentExecution、@PersistJobDataAfterExecution
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestJob1 implements Job {

    private static Logger logger = LoggerFactory.getLogger(TestJob1.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();

        logger.info("jobGroup={},jobName={},jobDesc={},triggerGroup={},triggerName={},triggerDesc={}",
                jobDetail.getKey().getGroup(),
                jobDetail.getKey().getName(),
                jobDetail.getDescription(),
                trigger.getKey().getGroup(),
                trigger.getKey().getName(),
                trigger.getDescription());
        Object url = mergedJobDataMap.get("url");
        System.out.println("hello testjob1" + url);

    }

}
