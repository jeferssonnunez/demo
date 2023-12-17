package com.globallogic.demo.model.entities;

import com.globallogic.demo.model.dto.response.PhoneResponse;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "phones")
public class PhoneEntity {

    @Id
    private UUID id = UUID.randomUUID();

    private Long number;

    private String cityCode;

    private String countryCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity user;

    public PhoneEntity(){
        // Empty
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PhoneResponse toDto(){
        PhoneResponse phoneResponse = new PhoneResponse();

        phoneResponse.setNumber(this.number);
        phoneResponse.setCityCode(this.cityCode);
        phoneResponse.setCountryCode(this.countryCode);
        return phoneResponse;
    }
}
