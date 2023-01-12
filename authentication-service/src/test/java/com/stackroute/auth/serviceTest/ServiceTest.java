package com.stackroute.auth.serviceTest;

import com.stackroute.auth.model.User;
import com.stackroute.auth.dto.UserRole;
import com.stackroute.auth.exception.UserAlreadyExistsException;
import com.stackroute.auth.exception.UserIdAndPasswordMismatchException;
import com.stackroute.auth.exception.UserNotFoundException;
import com.stackroute.auth.repository.UserAuthenticationRepository;
import com.stackroute.auth.service.UserAuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTest {

    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;

    private User user;
    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationServiceImpl;

    Optional<User> optional;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setEmail("sumeet123@gmail.com");
        user.setPassword("12345678");
        user.setUserRole(UserRole.RESOURCE);
        optional = Optional.of(user);
    }

    @Test
    public void testSaveUserSuccess() throws UserAlreadyExistsException {

        Mockito.when(userAuthenticationRepository.save(user)).thenReturn(user);
        boolean flag = userAuthenticationServiceImpl.saveUser(user);
        assertEquals(true, flag);

    }


    @Test
    public void testSaveUserFailure() {

        Mockito.when(userAuthenticationRepository.findById("sumeet123@gmail.com")).thenReturn(optional);
        Mockito.when(userAuthenticationRepository.save(user)).thenReturn(user);
        assertThrows(
                UserAlreadyExistsException.class,
                () -> {
                    userAuthenticationServiceImpl.saveUser(user);
                });

    }

    @Test
    public void login() throws UserNotFoundException, UserIdAndPasswordMismatchException {
        Mockito.when(userAuthenticationRepository.findByEmailAndPassword("sumeet123@gmail.com", "12345678")).thenReturn(user);
        User fetchedUser = userAuthenticationServiceImpl.login(user);
        assertEquals("sumeet123@gmail.com", fetchedUser.getEmail());
    }
}
