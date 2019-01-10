package com.github.elbean.ignite.cache.api.models.validation.validators;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MaxDuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validate that the input duration does not exceed the maximum allowed duration.
 * @author BStilson
 */
public class MaxDurationValidator implements ConstraintValidator<MaxDuration, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaxDurationValidator.class);

    private String dateUnitField;
    private String durationField;
    private int maxDuration;

    @Override
    public void initialize(MaxDuration constraintAnnotation) {
        dateUnitField = constraintAnnotation.dateUnitField();
        durationField = constraintAnnotation.durationField();
        maxDuration = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean violations = false;
        DateUnit inputDateUnit = null;
        int inputDuration = 0;
        try {
            inputDateUnit = DateUnit.valueOf(BeanUtils.getProperty(value, dateUnitField));
            inputDuration = Integer.parseInt(BeanUtils.getProperty(value, durationField));
        } catch (Exception e) {
            LOGGER.trace("unable to get {} or {} from incoming parameters, validation cannot complete.", dateUnitField, durationField);
        }

        if (inputDateUnit == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("unable to determine Date Unit based on input")
                    .addPropertyNode(dateUnitField)
                    .addConstraintViolation();
            violations = true;
        }
        if (inputDuration == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("unable to determine duration based on input")
                    .addPropertyNode(durationField)
                    .addConstraintViolation();
            violations = true;
        }
        //short circuit the logic if the fields could not be read from the input data
        if (violations) {
            return false;
        }
        int convertedInputDuration = convertDurationBasedOnDateUnit(inputDuration, inputDateUnit);
        if (convertedInputDuration <= maxDuration) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("maximum duration of %s %s", inputDuration, inputDateUnit))
                    .addPropertyNode(durationField)
                    .addConstraintViolation();
        }
        return false;
    }

    private int convertDurationBasedOnDateUnit(int inputDuration, DateUnit inputDateUnit) {
        switch (inputDateUnit) {
            case SECONDS:
                break;
            case MINUTES:
                inputDuration = inputDuration * 60;
                break;
            case HOURS:
                inputDuration = inputDuration * 60 * 60;
                break;
            default:
                throw new IllegalArgumentException("Unsupported input DateUnit");
        }
        return inputDuration;
    }
}
