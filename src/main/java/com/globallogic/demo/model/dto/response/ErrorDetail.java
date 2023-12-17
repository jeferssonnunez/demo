package com.globallogic.demo.model.dto.response;

import java.time.Instant;

public class ErrorDetail {
    private Instant timestamp;

    private Integer code;

    private String detail;

    public ErrorDetail(){
        // Empty
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
