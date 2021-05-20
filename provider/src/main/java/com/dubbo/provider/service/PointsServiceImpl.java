package com.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.PointsService;
import com.dubbo.api.entity.Points;
import com.dubbo.provider.dao.PointsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsMapper pointsMapper;

    /**
     * 增加积分
     * @param points
     */
    @Override
    public void addPoints(Points points){
        pointsMapper.addPoints(points);
    }

    /**
     * 扣减积分
     * @param points
     */
    @Override
    public void reducePoints(Points points){
        pointsMapper.reducePoints(points);
    }

    /**
     * 实现用户签到功能
     * @param points
     */
    @Override
    public void signIn(int points){
        Points p = new Points();
        p.setPoints(points);
        p.setId(1);
        pointsMapper.addPoints(p);
        System.out.println("用戶已成功签到!");
    }



}
