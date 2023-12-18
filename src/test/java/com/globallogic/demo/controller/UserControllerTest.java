package com.globallogic.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.demo.exceptions.ExistingUserException;
import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserDetailResponse;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.service.UserService;
import com.globallogic.demo.util.UserRequestBuilder;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

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
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test", null));

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
                .andExpect(jsonPath("$.active", is(userResponseMock.getActive())))
                .andExpect(jsonPath("$.token", instanceOf(String.class)));
    }

    @Test
    public void givenExistingUser_whenSaveUser_thenReturnJsonErrorr()
            throws Exception {
        UserResponse userResponseMock = getUserResponse();
        UserRequest userRequestMock = new UserRequestBuilder().buildUserRequest();

        String dtoAsString = mapper.writeValueAsString(userRequestMock);
        when(userService.addUser(any())).thenThrow(new ExistingUserException("Test existing user"));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsString)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @ParameterizedTest(name = "#{index} - Run test with wrong request = {0}")
    @MethodSource("invalidRequestProvider")
    public void givenUserWithInvalidRequest_whenSaveUser_thenReturnJsonError()
            throws Exception {
        UserResponse userResponseMock = mock(UserResponse.class);
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

    @Test
    public void givenUser_whenGetUser_thenReturnJsonUser()
            throws Exception {
        UserDetailResponse userDetailsResponseMock = getUserDetailMock();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userService.getUser(any())).thenReturn(userDetailsResponseMock);

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDetailsResponseMock.getName())))
                .andExpect(jsonPath("$.email", is(userDetailsResponseMock.getEmail())))
                .andExpect(jsonPath("$.password", is(userDetailsResponseMock.getPassword())))
                .andExpect(jsonPath("$.active", is(userDetailsResponseMock.getActive())))
                .andExpect(jsonPath("$.token", instanceOf(String.class)));
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

    private UserDetailResponse getUserDetailMock(){
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setName("test");
        userDetailResponse.setEmail("test@test.com");
        userDetailResponse.setPassword("a2asfGfdfdf4");
        userDetailResponse.setId(UUID.randomUUID());
        userDetailResponse.setCreated(Instant.now());
        userDetailResponse.setLastLogin(Instant.now());
        userDetailResponse.setToken("test");
        userDetailResponse.setActive(Boolean.TRUE);

        return userDetailResponse;
    }
}
