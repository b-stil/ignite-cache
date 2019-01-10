package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.validation.validators.MinDependsOnDateUnitValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Allows several <code>@MinDependsOnDateUnit</code> annotations on the same
 * element.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinDependsOnDateUnitValidator.class)
public @interface MinDependsOnDateUnits {

    String minValidAgainstField() default "";

    String dateUnitField() default "";

    MinDependsOnDateUnit[] value() default {};

    String message() default "{com.github.elbean.ignite.cache.models.validation.MinDependsOnDateUnits.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
