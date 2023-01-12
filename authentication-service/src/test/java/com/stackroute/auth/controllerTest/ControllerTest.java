package com.stackroute.auth.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.auth.controller.UserAuthenticationController;
import com.stackroute.auth.model.User;
import com.stackroute.auth.dto.UserRole;
import com.stackroute.auth.service.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;


    private User user;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;


    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthenticationController).build();

        user = new User();
        user.setEmail("sumeet123@gmail.com");
        user.setPassword("12345678");
        user.setUserRole(UserRole.RESOURCE);

    }

    @Test
    public void testRegisterUser() throws Exception {

        Mockito.when(userAuthenticationService.saveUser(user)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void testLoginUser() throws Exception {
        Mockito.when(userAuthenticationService.saveUser(user)).thenReturn(true);
        Mockito.when(userAuthenticationService.login(user)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }


    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try {

            ObjectMapper objmapper = new ObjectMapper();
            objmapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objmapper.registerModule(new JavaTimeModule());
            result = objmapper.writeValueAsString(obj);

        } catch (JsonProcessingException e) {
            result = "error";
        }
        return result;
    }
}
