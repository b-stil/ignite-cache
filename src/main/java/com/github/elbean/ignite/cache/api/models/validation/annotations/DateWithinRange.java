package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.validators.DateWithinRangeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation to validate that a DateTime falls within a specified range of
 * DateTime.now(DateTimeZone.UTC). Expects that the Annotated DateTime is in UTC
 * TimeZone.
 *
 * @author BStilson
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateWithinRangeValidator.class})
public @interface DateWithinRange {

    int range() default 5;

    DateUnit unit() default DateUnit.SECONDS;

    //standard validation
    String message() default "{com.github.elbean.ignite.cache.api.models.validators.DateWithinRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
