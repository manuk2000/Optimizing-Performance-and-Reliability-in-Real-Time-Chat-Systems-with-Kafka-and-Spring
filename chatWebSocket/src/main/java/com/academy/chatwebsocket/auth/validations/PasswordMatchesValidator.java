package com.academy.chatwebsocket.auth.validations;

import com.academy.chatwebsocket.auth.annotations.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object pass, ConstraintValidatorContext constraintValidatorContext) {
        String password = (String) pass;
        boolean containsLetter = false;
        boolean containsDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                containsLetter = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }

            if (containsLetter && containsDigit) {
                return true; // Password contains both letter and digit
            }
        }

        return false;
    }
}
