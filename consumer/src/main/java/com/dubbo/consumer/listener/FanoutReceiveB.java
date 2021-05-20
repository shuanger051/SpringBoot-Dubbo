package com.dubbo.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiveB {

    @RabbitHandler
    public void process(Map message){
        System.out.println("FanoutReceive B 消费者收到消息 ：" + message.toString());
    }

}
