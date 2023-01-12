package com.stackroute.search.service;

import com.stackroute.search.exception.UserProfileAlreadyExistsException;
import com.stackroute.search.exception.UserProfileNotFoundException;
import com.stackroute.search.model.UserProfile;
import com.stackroute.search.repository.UserProfileRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public UserProfile addUser(UserProfile userProfile) throws UserProfileAlreadyExistsException {
        Optional<UserProfile> existingUser=userProfileRepository.findById(userProfile.getEmail());
        if(existingUser.isPresent())
        {
            throw new UserProfileAlreadyExistsException("User already exists");
        }

        UserProfile newUserProfile=userProfileRepository.save(userProfile);

        if(newUserProfile==null)
        {
            return null;
        }
        return newUserProfile;
    }

    @Override
    public UserProfile updateUserDetails(String email, UserProfile userProfile) throws UserProfileNotFoundException {
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);

        if(existingUser.isPresent())
        {
            return userProfileRepository.save(userProfile);
        }

        throw new UserProfileNotFoundException();
    }

    @Override
    public UserProfile getUserProfileById(String email) throws UserProfileNotFoundException {
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);
        if(existingUser==null)
        {
            throw new UserProfileNotFoundException();
        }
        return existingUser.get();
    }

    @Override
    public boolean deleteUserProfileById(String email) {
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);
        if(existingUser.isPresent())
        {
            userProfileRepository.deleteById(email);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public SearchHits<UserProfile> search(String keyword) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("*"+keyword+"*")
                .field("email")
                .field("firstName")
                .field("lastName")
                .field("phoneNo")
                .field("gender")
                .field("userRole")
                .field("location")
                .field("designation")
                .field("domainExperiences.domainName")
                .field("skills.skillName")
                .field("skills.level")
                .field("completedProjects");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder).build();
        return elasticsearchRestTemplate.search(searchQuery, UserProfile.class);
    }
}
