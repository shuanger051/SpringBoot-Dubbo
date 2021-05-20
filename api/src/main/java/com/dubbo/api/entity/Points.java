package com.dubbo.api.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户积分实体类
 *  注：由于这里使用了RPC传输对象，所以对象必须要序列化，否则RPC将无法接收对象信息
 */
public class Points implements Serializable {

    private static final long serialVersionUID = -1698100163404377626L;

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    private Integer id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 积分数
     */
    @NotNull(message = "积分值不能为空")
    private Integer points;

    /**
     * 用户等级
     */
    private Integer userLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "Points{" +
                "id=" + id +
                ", userId=" + userId +
                ", points=" + points +
                ", userLevel=" + userLevel +
                '}';
    }

}
