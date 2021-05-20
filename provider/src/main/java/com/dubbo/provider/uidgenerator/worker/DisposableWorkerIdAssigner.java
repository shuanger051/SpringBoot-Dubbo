package com.dubbo.provider.uidgenerator.worker;

import com.dubbo.api.entity.WorkerNodeEntity;
import com.dubbo.provider.dao.WorkerNodeMapper;
import com.dubbo.provider.uidgenerator.utils.DockerUtils;
import com.dubbo.provider.uidgenerator.utils.NetUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);

    @Resource
    private WorkerNodeMapper workerNodeMapper;

    @Transactional
    public long assignWorkerId() {
        WorkerNodeEntity workerNodeEntity = buildWorkerNode();
        workerNodeMapper.addWorkerNode(workerNodeEntity);
        LOGGER.info("Add worker node:" + workerNodeEntity);
        return workerNodeEntity.getId();
    }

    private WorkerNodeEntity buildWorkerNode() {
        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());

        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            workerNodeEntity.setHostName(NetUtils.getLocalAddress());
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }

        return workerNodeEntity;
    }

}
