package com.globallogic.demo.service;

import com.globallogic.demo.model.entities.PhoneEntity;
import com.globallogic.demo.model.entities.UserEntity;
import com.globallogic.demo.model.dto.request.PhoneRequest;
import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse addUser(UserRequest userRequest){
        List<PhoneEntity> phones = Optional.ofNullable(userRequest.getPhones()).orElse(Collections.emptyList())
                .stream().map(PhoneRequest::toPhoneEntity).collect(Collectors.toList());

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setPhones(phones);
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setName(userRequest.getName());

        return userEntityToDto(userRepository.save(userEntity));
    }

    private UserResponse userEntityToDto(UserEntity userEntity){

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setActive(userEntity.getActive());
        userResponse.setCreated(userEntity.getCreated());
        userResponse.setLastLogin(userEntity.getLastLogin());

        return userResponse;
    }
}
