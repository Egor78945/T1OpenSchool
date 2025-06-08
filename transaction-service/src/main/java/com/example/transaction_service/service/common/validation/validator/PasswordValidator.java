package com.example.transaction_service.service.common.validation.validator;

import com.example.transaction_service.service.common.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank()) {
            return false;
        }
        int digitsCount = 0;
        int lettersCount = 0;
        for (char c : password.toLowerCase().toCharArray()) {
            if (c >= 97 && c <= 122) {
                lettersCount++;
            } else if (c >= 48 && c <= 57) {
                digitsCount++;
            } else {
                return false;
            }
        }
        return digitsCount >= 5 && lettersCount >= 5;
    }
}
