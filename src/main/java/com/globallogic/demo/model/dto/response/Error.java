package com.globallogic.demo.model.dto.response;

import java.util.List;

public class Error {

    private List<ErrorDetail> errors;

    public Error(){
        // Empty
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }
}
