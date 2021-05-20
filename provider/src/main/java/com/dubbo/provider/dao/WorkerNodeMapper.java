package com.dubbo.provider.dao;

import com.dubbo.api.entity.WorkerNodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface WorkerNodeMapper {

   public WorkerNodeEntity getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

   public void addWorkerNode(WorkerNodeEntity workerNodeEntity);

}