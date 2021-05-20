package com.dubbo.provider.dao;

import com.dubbo.api.entity.SchedulerEntity;
import com.dubbo.api.entity.SchedulerQueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SchedulerMapper {

    public List<SchedulerEntity> findSchedulers(SchedulerQueryEntity queryEntity);

}
