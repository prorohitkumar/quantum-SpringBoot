package com.stackroute.resourceallocate.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig
{
    @Value("${spring.rabbitmq.userExchange}")
    public String USER_EXCHANGE;

    @Value("${spring.rabbitmq.userResourceAllocationQueue}")
    public String USER_ALLOCATE_QUEUE;

    @Value("${spring.rabbitmq.userResourceAllocationRoutingKey}")
    public String USER_ALLOCATE_ROUTING_KEY;

    @Value("${spring.rabbitmq.exchange}")
    public String PROJECT_EXCHANGE;

    @Value("${spring.rabbitmq.projectAllocateQueue}")
    public String PROJECT_QUEUE;

    @Value("${spring.rabbitmq.projectAllocateRoutingKey}")
    public String PROJECT_ROUTING_KEY;

    @Value("${spring.rabbitmq.host}")
    public String HOST;

    @Value("${spring.rabbitmq.username}")
    public String USERNAME;

    @Value("${spring.rabbitmq.password}")
    public String PASSWORD;

    @Bean
    public Queue userAllocateQueue() { return new Queue(USER_ALLOCATE_QUEUE,true);}

    @Bean
    public DirectExchange userExchange()
    {
        return ExchangeBuilder.directExchange(USER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding userAllocateBinding()
    {
        return BindingBuilder
                .bind(userAllocateQueue())
                .to(userExchange())
                .with(USER_ALLOCATE_ROUTING_KEY);
    }

    @Bean
    public Queue projectQueue() {
        return new Queue(PROJECT_QUEUE, true);
    }

    @Bean
    public Exchange projectExchange() {
        return ExchangeBuilder.directExchange(PROJECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding projectBinding() {
        return BindingBuilder
                .bind(projectQueue())
                .to(projectExchange())
                .with(PROJECT_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    CachingConnectionFactory connectionFactory()
    {
        CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory(HOST);
        cachingConnectionFactory.setUsername(USERNAME);
        cachingConnectionFactory.setPassword(PASSWORD);

        return cachingConnectionFactory;
    }

}
