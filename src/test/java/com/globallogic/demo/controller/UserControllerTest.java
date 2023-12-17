package com.globallogic.demo.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenUser_whenSaveUser_thenReturnJsonUser()
            throws Exception {
        UserResponse userResponseMock = getUserResponse();
        String dtoAsString = mapper.writeValueAsString(getUserRequest());

        when(userService.addUser(any())).thenReturn(userResponseMock);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsString)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.active", is(userResponseMock.getActive())));
    }

    private UserRequest getUserRequest(){
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("test@test.com");
        userRequest.setName("test");
        userRequest.setPassword("pass");

        return userRequest;
    }

    private UserResponse getUserResponse(){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(UUID.randomUUID());
        userResponse.setCreated(Instant.now());
        userResponse.setLastLogin(Instant.now());
        userResponse.setToken("test");
        userResponse.setActive(Boolean.TRUE);

        return userResponse;
    }
}
