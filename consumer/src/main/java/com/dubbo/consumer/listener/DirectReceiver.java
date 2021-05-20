package com.dubbo.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
//屏蔽掉此行代码，主要是因为demo中，此行代码会与手动确认的配置产生冲突，产生负载均衡的效果，时而走这里，时而走那边。
//@RabbitListener(queues = "directQueue")
public class DirectReceiver {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

}

