package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.validation.validators.MaxDurationValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author BStilson
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxDurationValidator.class)
public @interface MaxDuration {

    /**
     * @return Maximum value of duration in SECONDS
     */
    int value();

    String durationField();
    
    String dateUnitField();
    
    String message() default "{com.github.elbean.ignite.cache.api.models.validation.MaxDuration.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
