package com.dubbo.api;

import com.dubbo.api.entity.Points;

public interface PointsService {

    /**
     * 增加积分
     * @param points
     */
    public void  addPoints(Points points);

    /**
     * 扣减积分
     * @param points
     */
    public void reducePoints(Points points);

    /**
     * 签到功能
     */
    public void signIn(int points);

}
