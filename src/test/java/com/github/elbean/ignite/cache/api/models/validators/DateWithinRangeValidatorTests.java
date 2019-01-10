package com.github.elbean.ignite.cache.api.models.validators;

import com.github.elbean.ignite.cache.api.models.validation.annotations.DateWithinRange;
import com.github.elbean.ignite.cache.api.models.DateUnit;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test that DateWithinRange annotation is working correctly.
 * @author BStilson
 */
public class DateWithinRangeValidatorTests extends AbstractValidatorTestFactory {

    @Test
    public void dateIsWithinSecondsRangeBehindPaddedShouldReturnTrue() {
        SecondsForDateWithinRange dto = new SecondsForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC));
        Set<ConstraintViolation<SecondsForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void dateIsNotWithinSecondsRangeBehindPaddedShouldReturnFalse() {
        SecondsForDateWithinRange dto = new SecondsForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).minusSeconds(20));
        Set<ConstraintViolation<SecondsForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void dateIsNotWithinSecondsRangeBeyondPaddedShouldReturnFalse() {
        SecondsForDateWithinRange dto = new SecondsForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).plusSeconds(20));
        Set<ConstraintViolation<SecondsForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void dateIsWithinMinutesRangeBehindPaddedShouldReturnTrue() {
        MinutesForDateWithinRange dto = new MinutesForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).minusMinutes(2));
        Set<ConstraintViolation<MinutesForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void dateIsNotWithinMinutesRangeBeyondPaddedShouldReturnFalse() {
        MinutesForDateWithinRange dto = new MinutesForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).plusMinutes(20));
        Set<ConstraintViolation<MinutesForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void dateIsWithinHoursRangeBehindPaddedShouldReturnTrue() {
        HoursForDateWithinRange dto = new HoursForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).plusMinutes(48));
        Set<ConstraintViolation<HoursForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void dateIsNotWithinHoursRangeBeyondPaddedShouldReturnFalse() {
        HoursForDateWithinRange dto = new HoursForDateWithinRange();
        dto.setStartDT(DateTime.now(DateTimeZone.UTC).plusMinutes(67));
        Set<ConstraintViolation<HoursForDateWithinRange>> violations = validator.validate(dto);
        Assert.assertFalse(violations.isEmpty());
    }

    private class SecondsForDateWithinRange {

        @DateWithinRange(range = 5, unit = DateUnit.SECONDS)
        DateTime startDT;

        public void setStartDT(DateTime startDT) {
            this.startDT = startDT;
        }
    }

    private class MinutesForDateWithinRange {

        @DateWithinRange(range = 5, unit = DateUnit.MINUTES)
        DateTime startDT;

        public void setStartDT(DateTime startDT) {
            this.startDT = startDT;
        }

    }
    
    private class HoursForDateWithinRange {

        @DateWithinRange(range = 1, unit = DateUnit.HOURS)
        DateTime startDT;

        public void setStartDT(DateTime startDT) {
            this.startDT = startDT;
        }

    }
}
