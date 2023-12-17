package com.globallogic.demo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {

    private UUID id;

    @JsonFormat(pattern="MMM dd, yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private Instant created;

    @JsonFormat(pattern="MMM dd, yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private Instant lastLogin;

    private String token;

    private Boolean isActive;

    public UserResponse(){
        // Empty
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
