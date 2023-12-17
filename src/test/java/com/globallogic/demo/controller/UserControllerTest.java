package com.globallogic.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.service.UserService;
import com.globallogic.demo.util.UserRequestBuilder;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before("")
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenUser_whenSaveUser_thenReturnJsonUser()
            throws Exception {
        UserResponse userResponseMock = getUserResponse();
        UserRequest userRequestMock = new UserRequestBuilder().buildUserRequest();

        String dtoAsString = mapper.writeValueAsString(userRequestMock);
        when(userService.addUser(any())).thenReturn(userResponseMock);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsString)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.active", is(userResponseMock.getActive())));
    }

    @ParameterizedTest(name = "#{index} - Run test with wrong request = {0}")
    @MethodSource("invalidRequestProvider")
    public void givenUserWithInvalidRequest_whenSaveUser_thenReturnJsonError()
            throws Exception {
        UserResponse userResponseMock = getUserResponse();
        UserRequest userRequestMock = new UserRequestBuilder()
                .email("test@")
                .buildUserRequest();

        String dtoAsString = mapper.writeValueAsString(userRequestMock);

        when(userService.addUser(any())).thenReturn(userResponseMock);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsString)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    static Stream<UserRequest> invalidRequestProvider() {
        UserRequest userRequestInvalidEmailMock = new UserRequestBuilder()
                .email("test@")
                .buildUserRequest();

        UserRequest userRequestInvalidPasswordMock = new UserRequestBuilder()
                .password("test")
                .buildUserRequest();

        return Stream.of(
                userRequestInvalidEmailMock,
                userRequestInvalidPasswordMock
        );
    }

    private UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(UUID.randomUUID());
        userResponse.setCreated(Instant.now());
        userResponse.setLastLogin(Instant.now());
        userResponse.setToken("test");
        userResponse.setActive(Boolean.TRUE);

        return userResponse;
    }
}
