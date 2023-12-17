package com.globallogic.demo.service;

import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.entities.UserEntity;
import com.globallogic.demo.repository.UserRepository;
import com.globallogic.demo.util.UserRequestBuilder;
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
        UserRequest userRequestMock = new UserRequestBuilder().buildUserRequest();

        when(userRepository.save(any())).thenReturn(userEntityMock);

        userService.addUser(userRequestMock);

        verify(userRepository).save(any());
    }
}
