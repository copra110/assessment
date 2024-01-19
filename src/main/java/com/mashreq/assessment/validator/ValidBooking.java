package com.mashreq.assessment.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookingValidator.class})
public @interface ValidBooking {
    String message() default "Validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
