package com.dubbo.api.entity;

import java.io.Serializable;

public class SchedulerQueryEntity implements Serializable {

    private static final long serialVersionUID = -1357088121573022420L;

    private Integer pageNum;

    private Integer pageSize;

    private String jobName;

    private String jobGroup;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    @Override
    public String toString() {
        return "SchedulerQueryEntity{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                '}';
    }

}
