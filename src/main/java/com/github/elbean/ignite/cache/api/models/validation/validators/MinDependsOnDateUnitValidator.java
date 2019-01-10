package com.github.elbean.ignite.cache.api.models.validation.validators;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validation to check that the duration is correct based on the DateUnit
 * supplied. Uses <code>@MinDependsOnDateUnits</code> and
 * <code>@MinDependsOnDateUnit</code> annotations to declare settings.
 *
 * @author BStilson
 */
public class MinDependsOnDateUnitValidator implements ConstraintValidator<MinDependsOnDateUnits, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinDependsOnDateUnitValidator.class);

    private String dateUnitField;
    private String minValidAgainstField;

    @Override
    public void initialize(MinDependsOnDateUnits constraintAnnotation) {
        minValidAgainstField = constraintAnnotation.minValidAgainstField();
        dateUnitField = constraintAnnotation.dateUnitField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //if nothing came in then there is a problem
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("no incoming paramters were found")
                    .addPropertyNode("incomingParams")
                    .addConstraintViolation();
            return false;
        }
        DateUnit inputDateUnit = null;
        int inputDuration = 0;
        try {
            inputDateUnit = DateUnit.valueOf(BeanUtils.getProperty(value, dateUnitField));
            inputDuration = Integer.parseInt(BeanUtils.getProperty(value, minValidAgainstField));
        } catch (Exception e) {
            LOGGER.trace("unable to get {} or {} from incoming parameters, validation cannot complete.", dateUnitField, minValidAgainstField);
            context.disableDefaultConstraintViolation();
            if (inputDateUnit == null) {
                context.buildConstraintViolationWithTemplate("unable to determine DateUnit based on input")
                        .addPropertyNode(dateUnitField)
                        .addConstraintViolation();
            }
            if (inputDuration == 0) {
                context.buildConstraintViolationWithTemplate("unable to determine duration based on input")
                        .addPropertyNode(minValidAgainstField)
                        .addConstraintViolation();
            }
            return false;
        }
        int minDuration = 0;
        MinDependsOnDateUnit[] comps = value.getClass().getAnnotationsByType(MinDependsOnDateUnit.class);
        for (MinDependsOnDateUnit m : comps) {
            if (m.unit() == inputDateUnit) {
                minDuration = m.minValue();
                LOGGER.trace("Input date unit '{}'; input duration '{}' required min value of '{}'.", inputDateUnit, inputDuration, minDuration);
                if (inputDuration >= minDuration) {
                    return true;
                } else {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(String.format("minimum of %s %s required", minDuration, inputDateUnit))
                            .addPropertyNode(minValidAgainstField)
                            .addConstraintViolation();
                }
            }
        }
        return false;
    }
}
