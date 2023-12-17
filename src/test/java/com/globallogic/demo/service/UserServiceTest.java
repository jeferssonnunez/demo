package com.globallogic.demo.service;

import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.entities.UserEntity;
import com.globallogic.demo.repository.UserRepository;
import com.globallogic.demo.util.UserRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final UserEntity userEntityMock = mock(UserEntity.class);

    @Test
    public void whenValidUser_thenSaveUser() {
        UserRequest userRequestMock = new UserRequestBuilder().buildUserRequest();

        when(userRepository.save(any())).thenReturn(userEntityMock);

        userService.addUser(userRequestMock);

        verify(userRepository).save(any());
    }

    @Test
    public void whenValidUser_thenReturnUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userEntityMock));

        userService.getUser("test@test.com");

        verify(userRepository).findByEmail(any());
        verify(userRepository).save(any());
    }

    @Test
    public void whenThereIsNoUser_thenThrowException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userService.getUser("test@test.com");
        });

        verify(userRepository).findByEmail(any());
        verifyNoMoreInteractions(userRepository);
    }
}
