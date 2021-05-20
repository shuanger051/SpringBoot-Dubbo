package com.dubbo.provider.service;

import com.dubbo.api.ThreadService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadServiceImpl implements ThreadService {

    @Override
    @Async
    public void testThread(int i){
        System.out.println("线程  " + Thread.currentThread().getName() + "  :执行异步任务" + i);
    }

    @Override
    public void normalMethod(int i){
        System.out.println("线程  " + Thread.currentThread().getName() + "  :执行任务" + i);
    }

}
