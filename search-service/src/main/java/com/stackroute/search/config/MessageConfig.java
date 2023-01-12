package com.stackroute.search.config;

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
public class MessageConfig {

    @Value("${spring.rabbitmq.userQueue}")
    public String USER_QUEUE;

    @Value("${spring.rabbitmq.userExchange}")
    public String USER_EXCHANGE;

    @Value("${spring.rabbitmq.userRoutingkey}")
    public String USER_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectQueue}")
    public String PROJECT_QUEUE;

    @Value("${spring.rabbitmq.projectExchange}")
    public String PROJECT_EXCHANGE;

    @Value("${spring.rabbitmq.projectRoutingkey}")
    public String PROJECT_ROUTING_KEY;

    @Value("${spring.rabbitmq.host}")
    public String HOST;

    @Value("${spring.rabbitmq.username}")
    public String USERNAME;

    @Value("${spring.rabbitmq.password}")
    public String PASSWORD;

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Exchange userExchange() {
        return ExchangeBuilder.directExchange(USER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(userExchange())
                .with(USER_ROUTING_KEY)
                .noargs();
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
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(HOST);
        cachingConnectionFactory.setUsername(USERNAME);
        cachingConnectionFactory.setPassword(PASSWORD);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
