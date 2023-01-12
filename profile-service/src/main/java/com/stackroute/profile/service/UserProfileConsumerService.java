package com.stackroute.profile.service;

import com.stackroute.profile.convertor.UserProfileConvertor;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.repository.UserProfileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileConsumerService implements RabbitListenerConfigurer
{
    @Autowired
    private UserProfileRepository userProfileRepository;

    UserProfileConvertor converter = new UserProfileConvertor();

    @RabbitListener(queues = "${spring.rabbitmq.projectProfileListQueue}")
    public void receivedMessages(List<String> userProfileIdList)
    {
        if(userProfileIdList.isEmpty()==false)
        {
            for(String userProfileId: userProfileIdList)
            {
                if(userProfileRepository.findById(userProfileId).isPresent()) {
                    UserProfile userProfile = userProfileRepository.findById(userProfileId).get();
                    userProfile.setAvailableForProject(false);
                    userProfile.setShowNotification(true);
                    userProfileRepository.save(userProfile);
                }
            }
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {

    }
}
