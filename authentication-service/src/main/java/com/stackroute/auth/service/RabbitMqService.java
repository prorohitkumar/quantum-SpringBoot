package com.stackroute.auth.service;

import com.stackroute.auth.dto.UserDTO;
import com.stackroute.auth.model.User;
import com.stackroute.auth.exception.UserAlreadyExistsException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqService implements RabbitListenerConfigurer {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void recivedMessage(UserDTO userDTO) throws UserAlreadyExistsException {
        try {
            if (userDTO.getAction().equals("create")) {
                User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getUserRole());
                userAuthenticationService.saveUser(user);
            }

        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException("");
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }
}

