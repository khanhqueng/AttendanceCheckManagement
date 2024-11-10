package com.security.Jwt_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.cglib.core.Local;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Constraint(validatedBy = DateCheckingValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateCheck {
    String startDate();
    String endDate();
    String message() default "Start date must before End date !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        DateCheck[] value();
    }
}
