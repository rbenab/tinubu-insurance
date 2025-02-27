package com.tinubu.insurance.exception;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateBeforeEndDateValidator.class)
@Documented
public @interface StartDateBeforeEndDate {
    String message() default "La date de fin doit être après la date de début et dans le futur";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
