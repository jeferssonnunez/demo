package com.globallogic.demo.model.dto.request;

import com.globallogic.demo.model.entities.PhoneEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PhoneRequest {

    @NotNull(message = "Number is mandatory")
    private Long number;

    @NotBlank(message = "City code is mandatory")
    private String cityCode;

    @NotBlank(message = "Country code is mandatory")
    private String countryCode;

    public PhoneRequest(){
        // Empty
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

    public PhoneEntity toPhoneEntity(){
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(this.getNumber());
        phoneEntity.setCityCode(this.getCityCode());
        phoneEntity.setCountryCode(this.getCountryCode());

        return phoneEntity;
    }
}
