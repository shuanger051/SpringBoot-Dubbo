package com.dubbo.api.entity;

import java.io.Serializable;
import java.util.Map;

public class SchedulerEntity implements Serializable {

    private static final long serialVersionUID = 4047503485182715276L;

    private String schedName;

    private String jobName;

    private String jobGroup;

    private String jobDesc;

    private String jobClassName;

    private String jobData;

    private String triggerName;

    private String triggerGroup;

    private String triggerState;

    private String triggerDesc;

    private String triggerType;

    private String triggerData;

    private String cronExpression;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerDesc() {
        return triggerDesc;
    }

    public void setTriggerDesc(String triggerDesc) {
        this.triggerDesc = triggerDesc;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerData() {
        return triggerData;
    }

    public void setTriggerData(String triggerData) {
        this.triggerData = triggerData;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "schedName='" + schedName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", jobClassName='" + jobClassName + '\'' +
                ", jobData=" + jobData +
                ", triggerName='" + triggerName + '\'' +
                ", triggerGroup='" + triggerGroup + '\'' +
                ", triggerState='" + triggerState + '\'' +
                ", triggerDesc='" + triggerDesc + '\'' +
                ", triggerType='" + triggerType + '\'' +
                ", triggerData=" + triggerData +
                ", cronExpression='" + cronExpression + '\'' +
                '}';
    }

}
