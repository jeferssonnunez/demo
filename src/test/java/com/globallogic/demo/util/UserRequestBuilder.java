package com.globallogic.demo.util;

import com.globallogic.demo.model.dto.request.PhoneRequest;
import com.globallogic.demo.model.dto.request.UserRequest;

import java.util.Collections;
import java.util.List;

public class UserRequestBuilder {
    private String name = "test";

    private String email = "test@test.com";

    private String password = "a2asfGfdfdf4";

    private List<PhoneRequest> phones = Collections.emptyList();

    public UserRequestBuilder() {
        // Empty
    }

    public UserRequest buildUserRequest() {
        return new UserRequest(name, email, password, phones);
    }

    public UserRequestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserRequestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserRequestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserRequestBuilder phones(List<PhoneRequest> phones) {
        this.phones = phones;
        return this;
    }
}
