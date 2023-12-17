package com.globallogic.demo.service;

import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.entities.UserEntity;
import com.globallogic.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void whenValidUser_thenSaveUser() {
        UserEntity userEntityMock = mock(UserEntity.class);

        when(userRepository.save(any())).thenReturn(userEntityMock);

        userService.addUser(getUserRequest());

        verify(userRepository).save(any());
    }

    private UserRequest getUserRequest(){
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("test@test.com");
        userRequest.setName("test");
        userRequest.setPassword("pass");

        return userRequest;
    }
}
