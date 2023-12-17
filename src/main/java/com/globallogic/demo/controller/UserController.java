package com.globallogic.demo.controller;

import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserDetailResponse;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest){

        return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDetailResponse> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }
}
