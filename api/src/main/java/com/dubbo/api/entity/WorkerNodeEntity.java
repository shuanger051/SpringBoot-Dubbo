package com.dubbo.api.entity;

import java.util.Date;

public class WorkerNodeEntity {

    private long id;

    private String hostName;

    private String port;

    private int type;

    private Date launchDate = new Date();

    private Date created;

    private Date modified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "WorkerNodeEntity{" +
                "id=" + id +
                ", hostName='" + hostName + '\'' +
                ", port='" + port + '\'' +
                ", type=" + type +
                ", launchDate=" + launchDate +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

}
