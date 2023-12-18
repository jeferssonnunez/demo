package com.globallogic.demo.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private static final String ONE_UPPER_CASE = "^[^A-Z]*[A-Z][^A-Z]*$";
    private static final String TWO_NUMBERS = "^([^0-9]*[0-9][^0-9]*)([^0-9]*[0-9][^0-9]*)$";

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(ONE_UPPER_CASE) && value.matches(TWO_NUMBERS);
    }
}
