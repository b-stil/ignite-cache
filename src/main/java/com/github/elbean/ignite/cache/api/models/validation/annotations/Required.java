package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.validation.validators.RequiredValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation to indicate that an incoming request parameter (QueryString, DTO, URI Parameter) is required.
 * This means that the parameter cannot be null or empty.
 * 
 * @author BStilson
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RequiredValidator.class})
public @interface Required {
    //standard validation
    String message() default "is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
