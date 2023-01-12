package com.stackroute.auth.repository;

import com.stackroute.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAuthenticationRepository extends JpaRepository<User,String>{

	public User findByEmailAndPassword(String email, String password);


}
