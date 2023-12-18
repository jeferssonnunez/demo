package com.globallogic.demo.service;

import com.globallogic.demo.exceptions.ExistingUserException;
import com.globallogic.demo.model.dto.response.PhoneResponse;
import com.globallogic.demo.model.dto.response.UserDetailResponse;
import com.globallogic.demo.model.entities.PhoneEntity;
import com.globallogic.demo.model.entities.UserEntity;
import com.globallogic.demo.model.dto.request.PhoneRequest;
import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.repository.UserRepository;
import com.globallogic.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public UserResponse addUser(UserRequest userRequest){
        findExistingUser(userRequest.getEmail());

        Set<PhoneEntity> phones = Optional.ofNullable(userRequest.getPhones()).orElse(Collections.emptyList())
                .stream().map(PhoneRequest::toPhoneEntity).collect(Collectors.toSet());

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setPhones(phones);
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setName(userRequest.getName());

        phones.forEach(phoneEntity -> phoneEntity.setUser(userEntity));

        return userEntityToDto(userRepository.save(userEntity));
    }

    private void findExistingUser(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        userEntityOptional.ifPresent(userEntity -> {
            throw new ExistingUserException("The user already exist: "+ email);
        } );
    }

    public UserDetailResponse getUser(String email){
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        UserDetailResponse userDetailResponse = userEntityOptional.map(this::userEntityToDetailDto).orElseThrow();

        // Update last login
        UserEntity userEntity = userEntityOptional.get();
        userEntity.setLastLogin(Instant.now());
        userRepository.save(userEntity);

        return userDetailResponse;
    }

    private UserResponse userEntityToDto(UserEntity userEntity){
        // Move to a different class
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setActive(userEntity.getActive());
        userResponse.setCreated(userEntity.getCreated());
        userResponse.setLastLogin(userEntity.getLastLogin());
        userResponse.setToken(jwtUtil.generateToken(userEntity.getEmail()));

        return userResponse;
    }

    private UserDetailResponse userEntityToDetailDto(UserEntity userEntity){
        List<PhoneResponse> phones = Optional.ofNullable(userEntity.getPhones()).orElse(Collections.emptySet())
                .stream().map(PhoneEntity::toDto).collect(Collectors.toList());

        UserDetailResponse userResponse = new UserDetailResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setActive(userEntity.getActive());
        userResponse.setCreated(userEntity.getCreated());
        userResponse.setLastLogin(userEntity.getLastLogin());
        userResponse.setToken(jwtUtil.generateToken(userEntity.getEmail())); // Invalidate current token
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setName(userEntity.getName());
        userResponse.setPassword(userEntity.getPassword());
        userResponse.setPhones(phones);

        return userResponse;
    }
}
