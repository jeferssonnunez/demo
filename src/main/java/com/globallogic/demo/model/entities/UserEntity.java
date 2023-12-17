package com.globallogic.demo.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String email;

    private String password;

    private Instant created = Instant.now();

    private Instant lastLogin = Instant.now();

    private Boolean isActive = Boolean.TRUE;

    @OneToMany(cascade= ALL, mappedBy="user")
    private Set<PhoneEntity> phones;

    public UserEntity() {
        // Empty
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<PhoneEntity> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneEntity> phones) {
        this.phones = phones;
    }
}
