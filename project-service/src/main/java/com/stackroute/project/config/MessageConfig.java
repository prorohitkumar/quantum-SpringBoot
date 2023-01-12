package com.stackroute.project.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {


    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE;

    @Value("${spring.rabbitmq.projectProfileQueue}")
    public String PROJECT_PROFILE_QUEUE;

    @Value("${spring.rabbitmq.projectProfileListQueue}")
    public String PROJECT_PROFILE_LIST_QUEUE;

    @Value("${spring.rabbitmq.projectSearchQueue}")
    public String PROJECT_SEARCH_QUEUE;

    @Value("${spring.rabbitmq.projectAllocateQueue}")
    public String PROJECT_ALLOCATE_QUEUE;

    @Value("${spring.rabbitmq.projectProfileRoutingkey}")
    public String PROJECT_PROFILE_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectProfileListRoutingKey}")
    public String PROJECT_PROFILE_LIST_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectAllocateRoutingKey}")
    public String PROJECT_ALLOCATE_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectSearchRoutingkey}")
    public String PROJECT_SEARCH_ROUTING_KEY;

    @Value("${spring.rabbitmq.host}")
    public String HOST;

    @Value("${spring.rabbitmq.username}")
    public String USERNAME;

    @Value("${spring.rabbitmq.password}")
    public String PASSWORD;

    @Bean
    public Queue projectProfileQueue()
    {
        return new Queue(PROJECT_PROFILE_QUEUE);
    }

    @Bean
    public Queue projectProfileListQueue()
    {
        return new Queue(PROJECT_PROFILE_LIST_QUEUE, true);
    }

    @Bean
    public Queue projectSearchQueue()
    {
        return new Queue(PROJECT_SEARCH_QUEUE);
    }

    @Bean
    public Queue projectAllocateQueue()
    {
        return new Queue(PROJECT_ALLOCATE_QUEUE);
    }

    @Bean
    public DirectExchange exchange()
    {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding projectProfileBinding() {
        return BindingBuilder.bind(projectProfileQueue()).to(exchange()).with(PROJECT_PROFILE_ROUTING_KEY);
    }

    @Bean
    public Binding projectProfileListBinding()
    {
        return BindingBuilder.bind(projectProfileListQueue()).to(exchange()).with(PROJECT_PROFILE_LIST_ROUTING_KEY);
    }

    @Bean
    public Binding projectSearchBinding() {
        return BindingBuilder.bind(projectSearchQueue()).to(exchange()).with(PROJECT_SEARCH_ROUTING_KEY);
    }

    @Bean
    public Binding projectAllocateBinding()
    {
        return BindingBuilder.bind(projectAllocateQueue()).to(exchange()).with(PROJECT_ALLOCATE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory(HOST);
        cachingConnectionFactory.setUsername(USERNAME);
        cachingConnectionFactory.setPassword(PASSWORD);

        return cachingConnectionFactory;
    }

}
