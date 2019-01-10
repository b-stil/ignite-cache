package com.github.elbean.ignite.cache.api.models.validators;

import com.github.elbean.ignite.cache.api.models.validation.validators.ValueInOptionsValidator;
import com.github.elbean.ignite.cache.api.models.validation.validators.DateWithinRangeValidator;
import com.github.elbean.ignite.cache.api.models.validation.validators.MinDependsOnDateUnitValidator;
import com.github.elbean.ignite.cache.api.models.validation.validators.RequiredValidator;
import javax.validation.Configuration;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;

/**
 * Factory class to instantiate the appropriate <code>ConstraintValidator<code> for unit
 * testing purposes.
 *
 * @author BStilson
 */
public abstract class AbstractValidatorTestFactory implements ConstraintValidatorFactory {

    protected Validator validator;

    @SuppressWarnings("unchecked")
    @Before
    public void init() throws Exception {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        config.constraintValidatorFactory(this);
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        if (key == ValueInOptionsValidator.class) {
            return (T) new ValueInOptionsValidator();
        }
        if (key == DateWithinRangeValidator.class) {
            return (T) new DateWithinRangeValidator();
        }
        if (key == RequiredValidator.class) {
            return (T) new RequiredValidator();
        }
        if (key == MinDependsOnDateUnitValidator.class) {
            return (T) new MinDependsOnDateUnitValidator();
        }
        throw new IllegalArgumentException("Unconfigured validator requested");
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
