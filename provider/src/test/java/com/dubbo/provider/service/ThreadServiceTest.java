package com.dubbo.provider.service;

import com.dubbo.api.ThreadService;
import com.dubbo.provider.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadServiceTest extends BaseTest {

    @Autowired
    private ThreadService threadService;

    @Test
    public void threadTest(){
        for(int i = 0 ;i < 5000; i++){
            threadService.testThread(i);
        }
    }

    @Test
    public void normalTest(){
        for(int i = 0 ;i < 5000; i++){
            threadService.normalMethod(i);
        }
    }

}
