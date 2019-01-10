package com.github.elbean.ignite.cache.api.models.validators;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnits;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnit;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for <code>MinDependsOnDateUnitValidator</code>.
 * 
 * @author BStilson
 */
public class MinDependsOnDateUnitValidatorTests extends AbstractValidatorTestFactory {

    @Test
    public void minDependsOnDateUnitValidatorWithCorrectSecondsReturnsTrue() {
        MinDependsOnDateUnitTest testData = new MinDependsOnDateUnitTest();
        testData.setDuration(15);
        testData.setDateUnit(DateUnit.SECONDS);
        Set<ConstraintViolation<MinDependsOnDateUnitTest>> violations = validator.validate(testData);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void minDependsOnDateUnitValidatorWithIncorrectSecondsReturnsFalse() {
        MinDependsOnDateUnitTest testData = new MinDependsOnDateUnitTest();
        testData.setDuration(5);
        testData.setDateUnit(DateUnit.SECONDS);
        Set<ConstraintViolation<MinDependsOnDateUnitTest>> violations = validator.validate(testData);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void minDependsOnDateUnitValidatorWithCorrectMinutesReturnsTrue() {
        MinDependsOnDateUnitTest testData = new MinDependsOnDateUnitTest();
        testData.setDuration(5);
        testData.setDateUnit(DateUnit.MINUTES);
        Set<ConstraintViolation<MinDependsOnDateUnitTest>> violations = validator.validate(testData);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void minDependsOnDateUnitValidatorWithIncorrectMinutesReturnsFalse() {
        MinDependsOnDateUnitTest testData = new MinDependsOnDateUnitTest();
        testData.setDuration(1);
        testData.setDateUnit(DateUnit.MINUTES);
        Set<ConstraintViolation<MinDependsOnDateUnitTest>> violations = validator.validate(testData);
        Assert.assertFalse(violations.isEmpty());
    }

    @MinDependsOnDateUnits(dateUnitField = "dateUnit", minValidAgainstField = "duration", value = {
        @MinDependsOnDateUnit(minValue = 10, unit = DateUnit.SECONDS)
        ,
        @MinDependsOnDateUnit(minValue = 3, unit = DateUnit.MINUTES)
        ,
        @MinDependsOnDateUnit(minValue = 1, unit = DateUnit.HOURS)
    })
    public class MinDependsOnDateUnitTest {

        int duration;

        DateUnit dateUnit;

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public void setDateUnit(DateUnit dateUnit) {
            this.dateUnit = dateUnit;
        }

        public int getDuration() {
            return duration;
        }

        public DateUnit getDateUnit() {
            return dateUnit;
        }

    }
}
