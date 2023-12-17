package com.globallogic.demo.model.dto.response;

import java.util.List;

public class UserDetailResponse extends UserResponse {
    private String name;

    private String password;

    private String email;

    private List<PhoneResponse> phones;

    public UserDetailResponse(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhoneResponse> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneResponse> phones) {
        this.phones = phones;
    }
}
