package com.dubbo.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiveC {

    @RabbitHandler
    public void process(Map message){
        System.out.println("FanoutReceive C 消费者受到消息 ：" + message.toString());
    }

}
