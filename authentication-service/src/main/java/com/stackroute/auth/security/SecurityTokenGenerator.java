package com.stackroute.auth.security;

import com.stackroute.auth.model.User;
import java.util.Map;

public interface SecurityTokenGenerator {

    Map<String,String> generateToken(User user,String role);
}
