package com.dubbo.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitMQConfig {

    public final static String man = "topic.man";

    public final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue(){
        return new Queue(TopicRabbitMQConfig.man);
    }

    @Bean
    public Queue secondQueue(){
        return new Queue(TopicRabbitMQConfig.woman);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingExchangeMessageForMan(){
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
    }

    @Bean
    public Binding bindingExchangeMessageForWoman(){
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }



}
