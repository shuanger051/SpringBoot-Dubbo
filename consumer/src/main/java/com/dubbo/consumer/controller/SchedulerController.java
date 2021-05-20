package com.dubbo.consumer.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.SchedulerService;
import com.dubbo.api.entity.SchedulerEntity;
import com.dubbo.api.entity.SchedulerQueryEntity;
import com.dubbo.consumer.util.ResultUtils;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private static Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    @Reference
    private SchedulerService schedulerService;

    /**
     * 查询注册成功的任务信息列表
     * @param queryEntity
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/findSchedulers")
    public ResultUtils findSchedulers(SchedulerQueryEntity queryEntity) throws Exception{
        List<SchedulerEntity> queryList = schedulerService.findSchedulers(queryEntity);
        return ResultUtils.success(queryList);
    }

    /**
     * 注册任务
     * @param schedulerEntity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addSchedulers")
    public ResultUtils addSchedulers(@RequestBody SchedulerEntity schedulerEntity) throws Exception{
        //必须传入 cron_expression、job_class_name
        if (StringUtils.isBlank(schedulerEntity.getCronExpression()) || StringUtils.isBlank(schedulerEntity.getJobClassName())) {
            return ResultUtils.success(400,"cron_expression or job_class_name is blank");
        }
        schedulerService.addSchedulers(schedulerEntity);
        return ResultUtils.success(0,"添加成功");
    }

    /**
     * 清空任务
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/clearAllScheduler")
    public ResultUtils clearAllScheduler() throws Exception{
        schedulerService.clear();
        return ResultUtils.success(0,"清除成功");
    }

    /**
     * 重新注册任务
     * @param schedulerEntity
     * @return
     */
    @PostMapping(value = "/rescheduleJob")
    public ResultUtils rescheduleJob(@RequestBody SchedulerEntity schedulerEntity) throws Exception{
        //必须传入 cron_expression、job_class_name
        if (StringUtils.isBlank(schedulerEntity.getCronExpression())) {
            return ResultUtils.success(400,"cronExpression 参数不能为空");
        }

        Date nextDate = schedulerService.rescheduleJob(schedulerEntity);
        return ResultUtils.success(nextDate);
    }

    /**
     * 刪除指定的单个任务
     * @param queryEntity
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/deleteJob")
    public ResultUtils deleteJob(SchedulerQueryEntity queryEntity) throws Exception{
        schedulerService.deleteJob(queryEntity);
        return ResultUtils.success(0,"删除成功");
    }

    /**
     * 停止任务（针对全局）
     * 停止后无法通过start唤醒任务池，只能重新启动整个application
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/shutdownScheduler")
    public ResultUtils shutdownScheduler() throws Exception{
        schedulerService.shutdown();
        return ResultUtils.success(0,"关闭成功");
    }

    /**
     * 暂停任务
     * @param queryEntity
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/pauseJob")
    public ResultUtils pauseJob(SchedulerQueryEntity queryEntity) throws Exception{
        schedulerService.pauseJob(queryEntity);
        return ResultUtils.success(0,"暂停成功");
    }

    /**
     * 暂停全部任务
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/pauseAll")
    public ResultUtils pauseAll() throws Exception{
        schedulerService.pauseAll();
        return ResultUtils.success(0,"暂停成功");
    }

    /**
     * 唤醒任务
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/resumeJob")
    public ResultUtils resumeJob(@RequestBody SchedulerQueryEntity queryEntity) throws Exception{
        schedulerService.resumeJob(queryEntity);
        return ResultUtils.success(0,"唤醒成功");
    }

    /**
     * 唤醒全部任务
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/resumeAll")
    public ResultUtils resumeAll() throws Exception{
        schedulerService.resumeAll();
        return ResultUtils.success(0,"唤醒成功");
    }

}
