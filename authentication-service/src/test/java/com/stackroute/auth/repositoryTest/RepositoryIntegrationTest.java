package com.stackroute.auth.repositoryTest;

import com.stackroute.auth.model.User;
import com.stackroute.auth.dto.UserRole;
import com.stackroute.auth.repository.UserAuthenticationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.stackroute.auth.dto.UserRole.MANAGER;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class RepositoryIntegrationTest {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("sumeet123@gmail.com");
        user.setPassword("12345678");
        user.setUserRole(MANAGER);
    }

    @AfterEach
    public void tearDown() {
        userAuthenticationRepository.deleteAll();
        user = null;
    }

    @Test
    public void givenUserToSaveThenShouldReturnSavedUser() {
        userAuthenticationRepository.save(user);
        User getUser = userAuthenticationRepository.findById(user.getEmail()).get();
        assertEquals("sumeet123@gmail.com", getUser.getEmail());
    }


    @Test
    public void givenGetShouldReturnListOfAllUsers() {
        User user = new User("sumeet2@gmail.com", "123456789", UserRole.RESOURCE);
        User user1 = new User("sumeet3@gmail.com", "1234567890", UserRole.RESOURCE);
        userAuthenticationRepository.save(user);
        userAuthenticationRepository.save(user1);

        List<User> userList = (List<User>) userAuthenticationRepository.findAll();
        assertEquals("sumeet2@gmail.com", userList.get(0).getEmail());
    }

    @Test
    public void givenEmailThenShouldReturnAllRespectiveUsers() {
        User user = new User("sumeet2@gmail.com", "123456789", UserRole.RESOURCE);
        User user1 = userAuthenticationRepository.save(user);
        Optional<User> optional = userAuthenticationRepository.findById(user1.getEmail());
        assertEquals(user1.getEmail(), optional.get().getEmail());
        assertEquals(user1.getPassword(), optional.get().getPassword());
        assertEquals(user1.getUserRole(), optional.get().getUserRole());
    }

    @Test
    public void givenEmailToDeleteThenShouldReturnDeletedUser() {
        User user = new User("sumeet2@gmail.com", "123456789", UserRole.RESOURCE);
        userAuthenticationRepository.save(user);
        userAuthenticationRepository.deleteById(user.getEmail());
        Optional optional = userAuthenticationRepository.findById(user.getEmail());
        assertEquals(Optional.empty(), optional);
    }

}
