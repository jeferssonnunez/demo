package com.globallogic.demo.controller;

import com.globallogic.demo.model.dto.request.UserRequest;
import com.globallogic.demo.model.dto.response.UserResponse;
import com.globallogic.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest){

        return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
    }
}
