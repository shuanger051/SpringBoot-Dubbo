package com.dubbo.provider.quartz;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

@Configuration
public class QuartzConfig {

    /**
     * 通过SchedulerFactory获取Scheduler
     */
    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public Scheduler scheduler(){
        return schedulerFactoryBean.getScheduler();
    }

}
