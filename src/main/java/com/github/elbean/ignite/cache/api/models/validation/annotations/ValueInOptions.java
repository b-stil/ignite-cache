package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.validation.validators.ValueInOptionsValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Annotation to validate that a supplied String value exists as a Constant in an Enum.
* @author BStilson
*/
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValueInOptionsValidator.class})
public @interface ValueInOptions {

    Class<? extends Enum<?>> enumClass();
    //standard validation
    String message() default "{com.github.elbean.ignite.cache.api.models.validators.ValueInOptions.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
