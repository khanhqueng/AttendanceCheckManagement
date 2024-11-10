package com.security.Jwt_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateCheckingValidator implements ConstraintValidator<DateCheck, Object> {
    private String startDate;
    private String endDate;
    @Override
    public void initialize(DateCheck constraintAnnotation) {
        this.startDate= constraintAnnotation.startDate();
        this.endDate= constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Field startDateField = value.getClass().getDeclaredField(this.startDate);
            startDateField.setAccessible(true);
            Field endDateField = value.getClass().getDeclaredField(this.endDate);
            endDateField.setAccessible(true);
            LocalDate startDate = (LocalDate) startDateField.get(value);
            LocalDate endDate = (LocalDate) endDateField.get(value);
            if (startDate != null && endDate != null) {
                return startDate.isBefore(endDate);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
