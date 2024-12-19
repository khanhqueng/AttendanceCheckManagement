package com.security.Jwt_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StringEnumerationValidator implements ConstraintValidator<EnumCheck, String> {
    private Set<String> AVAILABLE_ENUM_NAMES;
    @Override
    public void initialize(EnumCheck constraintAnnotation) {
        Class<? extends Enum> enumSelected = constraintAnnotation.enumClass();
        AVAILABLE_ENUM_NAMES = (Set<String>) EnumSet.allOf(enumSelected).stream().map(e -> ((Enum<? extends Enum<?>>) e).name())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || AVAILABLE_ENUM_NAMES.contains(value)? true : false;
    }
}
