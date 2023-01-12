package com.stackroute.auth.model;

import com.stackroute.auth.dto.UserRole;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {


    @Id
    private String email;
    @NonNull
    private String password;
    @NonNull
    private UserRole userRole;


    public User() {
    }

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
