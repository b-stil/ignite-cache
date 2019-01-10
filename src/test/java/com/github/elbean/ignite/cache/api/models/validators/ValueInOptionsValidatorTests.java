package com.github.elbean.ignite.cache.api.models.validators;

import com.github.elbean.ignite.cache.api.models.validation.annotations.ValueInOptions;
import com.github.elbean.ignite.cache.api.models.DateUnit;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test that ValueInOptions annotation is working correctly.
 * @author BStilson
 */
public class ValueInOptionsValidatorTests extends AbstractValidatorTestFactory {

    @Test
    public void valueInOptionsValidatorValueAllowedReturnsTrue() {
        TestClass c = new TestClass();
        c.setTestValue("SECONDS");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(c);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void valueInOptionsValidatorValueNotAllowedReturnsFalse() {
        TestClass c = new TestClass();
        c.setTestValue("NANOSECONDS");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(c);
        Assert.assertFalse(violations.isEmpty());
    }

    private class TestClass{
        
        @ValueInOptions(enumClass = DateUnit.class)
        String testValue;
        
        public void setTestValue(String value){
            this.testValue = value;
        }
    }
}
