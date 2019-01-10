package com.github.elbean.ignite.cache.api.models.validation.validators;

import com.github.elbean.ignite.cache.api.models.validation.annotations.ValueInOptions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
* Validates that a supplied value is declared as a constant in an Enum.
* @author BStilson
*/
public class ValueInOptionsValidator implements ConstraintValidator<ValueInOptions, String> {

    private List<String> allowedValues;

    @Override
    public void initialize(ValueInOptions options) {
        Class<? extends Enum<?>> selected = options.enumClass();
        allowedValues = Stream.of(selected.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = allowedValues.contains(value);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("acceptable values are [%s]", StringUtils.join(allowedValues, ',')))
                .addConstraintViolation();
        return isValid;
    }
}
