package com.example.transaction_service.service.common.validation.annotation;

import com.example.transaction_service.service.common.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "invalid password format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
