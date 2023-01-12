package com.stackroute.profile.config;

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

    @Value("${spring.rabbitmq.userAuthQueue}")
    public String USER_AUTH_QUEUE;

    @Value("${spring.rabbitmq.userAuthRoutingkey}")
    public String USER_AUTH_ROUTING_KEY;

    @Value("${spring.rabbitmq.userSearchQueue}")
    public String USER_SEARCH_QUEUE;

    @Value("${spring.rabbitmq.userSearchRoutingkey}")
    public String USER_SEARCH_ROUTING_KEY;

    @Value("${spring.rabbitmq.userResourceAllocationQueue}")
    public String USER_ALLOCATE_QUEUE;

    @Value("${spring.rabbitmq.userResourceAllocationRoutingKey}")
    public String USER_ALLOCATE_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectExchange}")
    public String PROJECT_EXCHANGE;

    @Value("${spring.rabbitmq.projectQueue}")
    public String PROJECT_QUEUE;

    @Value("${spring.rabbitmq.projectRoutingkey}")
    public String PROJECT_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectProfileListQueue}")
    public String PROJECT_PROFILE_LIST_QUEUE;

    @Value("${spring.rabbitmq.projectProfileListRoutingKey}")
    public String PROJECT_PROFILE_LIST_ROUTING_KEY;

    @Value("${spring.rabbitmq.host}")
    public String HOST;

    @Value("${spring.rabbitmq.username}")
    public String USERNAME;

    @Value("${spring.rabbitmq.password}")
    public String PASSWORD;

    @Bean
    public Queue userAuthQueue()
    {
        return new Queue(USER_AUTH_QUEUE, true);
    }

    @Bean
    public Queue userSearchQueue()
    {
        return new Queue(USER_SEARCH_QUEUE, true);
    }

    //resource allocation queue
    @Bean
    public Queue userAllocateQueue() { return new Queue(USER_ALLOCATE_QUEUE,true);}

    @Bean
    public Queue projectQueue()  {
        return new Queue(PROJECT_QUEUE, true);
    }

    @Bean
    public Queue projectProfileListQueue()
    {
        return new Queue(PROJECT_PROFILE_LIST_QUEUE, true);
    }

    @Bean
    public DirectExchange userExchange()
    {
        return ExchangeBuilder.directExchange(USER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange projectExchange() {
        return ExchangeBuilder.directExchange(PROJECT_EXCHANGE).durable(true).build();
    }


    @Bean
    public Binding userAuthBinding() {
        return BindingBuilder
                .bind(userAuthQueue())
                .to(userExchange())
                .with(USER_AUTH_ROUTING_KEY);
    }

    @Bean
    public Binding userSearchBinding() {
        return BindingBuilder
                .bind(userSearchQueue())
                .to(userExchange())
                .with(USER_SEARCH_ROUTING_KEY);
    }

    //resource allocation service routing key
    @Bean
    public Binding userAllocateBinding()
    {
        return BindingBuilder
                .bind(userAllocateQueue())
                .to(userExchange())
                .with(USER_ALLOCATE_ROUTING_KEY);
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
    public Binding projectProfileListBinding()
    {
        return BindingBuilder
                .bind(projectProfileListQueue())
                .to(projectExchange())
                .with(PROJECT_PROFILE_LIST_ROUTING_KEY)
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