package com.globallogic.demo.model.dto.request;

import com.globallogic.demo.model.entities.PhoneEntity;

public class PhoneRequest {

    private Long number;

    private String cityCode;

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
