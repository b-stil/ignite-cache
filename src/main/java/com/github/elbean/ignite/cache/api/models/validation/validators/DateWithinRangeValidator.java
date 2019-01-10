package com.github.elbean.ignite.cache.api.models.validation.validators;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.DateWithinRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates that a given date falls within a range of DateTime units. Example:
 * Current DateTime of 9/14/2017 2:02:34 PM with a specified DateTime unit of
 * SECONDS and range of 5 checks that a supplied DateTime is between 2:02:29 PM
 * and 2:02:39 PM.
 *
 * @author BStilson
 */
public class DateWithinRangeValidator implements ConstraintValidator<DateWithinRange, DateTime> {

    final static Logger LOGGER = LoggerFactory.getLogger(DateWithinRangeValidator.class);

    private int validationRange;
    private DateUnit dateUnit;
    private DateTime comparison;

    @Override
    public void initialize(DateWithinRange constraintAnnotation) {
        validationRange = constraintAnnotation.range();
        dateUnit = constraintAnnotation.unit();
        comparison = DateTime.now(DateTimeZone.UTC);
    }

    @Override
    public boolean isValid(DateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Invalid date %s", (value != null) ? value.toString() : "null"))
                    .addConstraintViolation();
            return false;
        }
        boolean isValid = false;
        switch (dateUnit) {
            case SECONDS:
                isValid = validateSeconds(validationRange, value);
                break;
            case MINUTES:
                isValid = validateMinutes(validationRange, value);
                break;
            case HOURS:
                isValid = validateHours(validationRange, value);
                break;
            default:
                isValid = false;
                break;
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("is not within {range} {unit} of current DateTime '%s'", comparison.toString("yyyy-MM-dd'T'HH:mm:ss.SSSZ")))
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean validateSeconds(int seconds, DateTime toValidate) {
        DateTime rangeBeyond = comparison.plusSeconds(seconds);
        DateTime rangeBefore = comparison.minusSeconds(seconds);
        LOGGER.trace("Date to check: '{}'; range before: '{}'; range after: '{}'", toValidate.toString(), rangeBefore.toString(), rangeBeyond.toString());
        return toValidate.isAfter(rangeBefore) && toValidate.isBefore(rangeBeyond);
    }

    private boolean validateMinutes(int minutes, DateTime toValidate) {
        DateTime rangeBeyond = comparison.plusMinutes(minutes);
        DateTime rangeBefore = comparison.minusMinutes(minutes);
        LOGGER.trace("Date to check: '{}'; range before: '{}'; range after: '{}'", toValidate.toString(), rangeBefore.toString(), rangeBeyond.toString());
        return toValidate.isAfter(rangeBefore) && toValidate.isBefore(rangeBeyond);
    }

    private boolean validateHours(int hours, DateTime toValidate) {
        DateTime rangeBeyond = comparison.plusHours(hours);
        DateTime rangeBefore = comparison.minusHours(hours);
        LOGGER.trace("Date to check: '{}'; range before: '{}'; range after: '{}'", toValidate.toString(), rangeBefore.toString(), rangeBeyond.toString());
        return toValidate.isAfter(rangeBefore) && toValidate.isBefore(rangeBeyond);
    }
}
