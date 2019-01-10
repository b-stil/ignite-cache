package com.github.elbean.ignite.cache.api.models.validation.validators;

import com.github.elbean.ignite.cache.api.models.validation.annotations.Required;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates fields marked with Required annotations are not null or empty.
 * This does not account for minimum default values.
 * 
 * @author BStilson
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

    final static Logger LOGGER = LoggerFactory.getLogger(RequiredValidator.class);

    @Override
    public void initialize(Required constraintAnnotation) {
        //nothing special needed as it will only validate the value
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value == null) {
            isValid = false;
        }
        if (value instanceof String) {
            isValid = !((String) value).isEmpty();
        }
        if (value instanceof DateTime) {

        }
        LOGGER.trace("Required validation status: {}", isValid);
        return isValid;
    }

}
