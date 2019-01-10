package com.github.elbean.ignite.cache.api.models.validators;

import com.github.elbean.ignite.cache.api.models.validation.annotations.Required;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test that Required annotation is working correctly.
 *
 * @author BStilson
 */
public class RequiredValidatorTests extends AbstractValidatorTestFactory {

    @Test
    public void requiredValidatorPopulatedFieldShouldReturnTrue() {
        RequiredValidatorTest dto = new RequiredValidatorTest();
        dto.setStringField("TestValue");
        Set<ConstraintViolation<RequiredValidatorTest>> violations = validator.validate(dto);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void requiredValidatorEmptyStringShouldReturnFalse() {
        RequiredValidatorTest dto = new RequiredValidatorTest();
        dto.setStringField("");
        Set<ConstraintViolation<RequiredValidatorTest>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void requiredValidatorNullFieldShouldReturnFalse() {
        RequiredValidatorTest dto = new RequiredValidatorTest();
        dto.setStringField(null);
        Set<ConstraintViolation<RequiredValidatorTest>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    private class RequiredValidatorTest {

        @Required
        String stringField;

        public void setStringField(String value) {
            this.stringField = value;
        }
    }

}
