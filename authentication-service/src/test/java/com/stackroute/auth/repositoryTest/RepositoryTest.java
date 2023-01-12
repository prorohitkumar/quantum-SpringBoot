package com.stackroute.auth.repositoryTest;

import com.stackroute.auth.model.User;
import com.stackroute.auth.repository.UserAuthenticationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.stackroute.auth.dto.UserRole.MANAGER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {


    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setEmail("sumeet123@gmail.com");
        user.setPassword("12345678");
        user.setUserRole(MANAGER);
    }

    @AfterEach
    public void tearDown() throws Exception {
        userAuthenticationRepository.deleteAll();
    }


    @Test
    public void testRegisterUserSuccess() {
        userAuthenticationRepository.save(user);
        User getUser = userAuthenticationRepository.findById(user.getEmail()).get();
        assertThat(user.getEmail(), is(getUser.getEmail()));
    }

    @Test
    public void testLoginUserSuccess() {
        userAuthenticationRepository.save(user);
        User getUser = userAuthenticationRepository.findById(user.getEmail()).get();
        assertThat(user.getEmail(), is(getUser.getEmail()));
    }


}
