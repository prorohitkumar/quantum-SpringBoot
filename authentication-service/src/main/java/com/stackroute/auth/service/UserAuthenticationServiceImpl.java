package com.stackroute.auth.service;

import com.stackroute.auth.model.User;
import com.stackroute.auth.exception.UserAlreadyExistsException;
import com.stackroute.auth.exception.UserIdAndPasswordMismatchException;
import com.stackroute.auth.exception.UserNotFoundException;
import com.stackroute.auth.repository.UserAuthenticationRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;


@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private UserAuthenticationRepository userAuthRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    @Autowired
    public UserAuthenticationServiceImpl(UserAuthenticationRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public User login(User user) throws UserNotFoundException, UserIdAndPasswordMismatchException {


        User getUserCredentials = userAuthRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (getUserCredentials == null) {
            throw new UserIdAndPasswordMismatchException();
        }
        return getUserCredentials;

    }



    @Override
    public boolean saveUser(User user) throws UserAlreadyExistsException {
            Optional<User> optional1 = userAuthRepository.findById(user.getEmail());
            if (optional1.isPresent()) {
                throw new UserAlreadyExistsException("User Already Present");
            } else {
                userAuthRepository.save(user);
                return true;
           }


   }

    @Override
    public Object getAction(String userRole) throws IOException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        Object object = new Object();
        try {
            Resource resource = resourceLoader.getResource("classpath:action.json");
            JSONObject obj = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(resource.getInputStream())));
            JSONArray jsonArray = (JSONArray) obj.get("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject.get("role").equals(userRole)) {

                    object = jsonObject.get("action");

                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String getUserRole(String email) {
        Optional<User> userResult = userAuthRepository.findById(email);

        return userResult.get().getUserRole().toString();
    }


}
