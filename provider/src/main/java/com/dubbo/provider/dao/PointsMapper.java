package com.dubbo.provider.dao;

import com.dubbo.api.entity.Points;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PointsMapper {

    /**
     * 增加用户积分
     * @param points
     */
    public void addPoints(Points points);

    /**
     * 扣减用户积分
     * @param points
     */
    public void reducePoints(Points points);

}
