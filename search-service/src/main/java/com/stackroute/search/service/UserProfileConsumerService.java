package com.stackroute.search.service;

import com.stackroute.search.aspect.LoggerAspect;
import com.stackroute.search.converter.Converter;
import com.stackroute.search.dto.UserDTO;
import com.stackroute.search.model.UserProfile;
import com.stackroute.search.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileConsumerService implements RabbitListenerConfigurer {

    private UserProfileRepository userProfileRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    Converter converter = new Converter();

    @Autowired
    public UserProfileConsumerService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // This method consumes the message from Rabbitmq queue
    @RabbitListener(queues = "${spring.rabbitmq.userQueue}")
    public void recivedMessage(UserDTO userDTO) {
        String action = userDTO.getAction();
        if (action.equals("create")) {
            addUserFromQueue(userDTO);
        }
        if(action.equals("update")) {
            updateUserFromQueue(userDTO);
        }
    }

//    This method is executed when there is a create action
    public void addUserFromQueue(UserDTO userDTO) {
        UserProfile newUserProfile = converter.userDtoToUserProfile(userDTO);
        userProfileRepository.save(newUserProfile);
        logger.info("Added User "+userDTO.getEmail()+" successfully");
    }

//    This method is executed when there is a update action
    public void updateUserFromQueue(UserDTO userDTO) {
        UserProfile updatedUserProfile = converter.userDtoToUserProfile(userDTO);
        userProfileRepository.save(updatedUserProfile);
        logger.info("Added User "+userDTO.getEmail()+" successfully");
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
