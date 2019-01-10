package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.domain.applock.AppLock;
import com.github.elbean.ignite.cache.domain.applock.ExistingAppLockIsOwnedByRequesterRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

public class ExistingAppLockIsOwnedByRequesterRuleTests {

    @Test
    public void validateThatNullCandidateReturnsFalse() {
        AppLock existing = new AppLock();
        existing.setReferenceId(1);
        existing.setComponent("banana");
        existing.setLockOwner("Test");
        existing.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        existing.setExpirationDT(5, DateUnit.MINUTES);

        AppLock candidate = null;

        ExistingAppLockIsOwnedByRequesterRule rule = new ExistingAppLockIsOwnedByRequesterRule(existing);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse(result);
    }

    @Test
    public void validateThatNullExistingReturnsFalse() {
        AppLock existing = null;

        AppLock candidate = new AppLock();
        candidate.setReferenceId(1);
        candidate.setComponent("banana");
        candidate.setLockOwner("Test");
        candidate.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        candidate.setExpirationDT(5, DateUnit.MINUTES);

        ExistingAppLockIsOwnedByRequesterRule rule = new ExistingAppLockIsOwnedByRequesterRule(existing);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse(result);
    }

    @Test
    public void validateThatAnExistingAppLockOwnedByTheSameRequesterOnTheSameComponentReturnsTrue() {
        AppLock existing = new AppLock();
        existing.setReferenceId(1);
        existing.setComponent("banana");
        existing.setLockOwner("Test");
        existing.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        existing.setExpirationDT(5, DateUnit.MINUTES);

        AppLock candidate = new AppLock();
        candidate.setReferenceId(1);
        candidate.setComponent("banana");
        candidate.setLockOwner("Test");
        candidate.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        candidate.setExpirationDT(10, DateUnit.MINUTES);

        ExistingAppLockIsOwnedByRequesterRule rule = new ExistingAppLockIsOwnedByRequesterRule(existing);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertTrue(result);
    }

    @Test
    public void validateThatAnExistingAppLockOnTheSameComponentByADifferentRequestorReturnsFalse() {
        AppLock existing = new AppLock();
        existing.setReferenceId(1);
        existing.setComponent("banana");
        existing.setLockOwner("Test");
        existing.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        existing.setExpirationDT(5, DateUnit.MINUTES);

        AppLock candidate = new AppLock();
        candidate.setReferenceId(1);
        candidate.setComponent("banana");
        candidate.setLockOwner("Test2");
        candidate.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        candidate.setExpirationDT(10, DateUnit.MINUTES);

        ExistingAppLockIsOwnedByRequesterRule rule = new ExistingAppLockIsOwnedByRequesterRule(existing);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse(result);
    }

    @Test
    public void validateThatAnAppLockOnDifferentComponentsBySameRequesterReturnsFalse() {
        AppLock existing = new AppLock();
        existing.setReferenceId(1);
        existing.setComponent("banana");
        existing.setLockOwner("Test");
        existing.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        existing.setExpirationDT(5, DateUnit.MINUTES);

        AppLock candidate = new AppLock();
        candidate.setReferenceId(1);
        candidate.setComponent("orange");
        candidate.setLockOwner("Test");
        candidate.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        candidate.setExpirationDT(10, DateUnit.MINUTES);

        ExistingAppLockIsOwnedByRequesterRule rule = new ExistingAppLockIsOwnedByRequesterRule(existing);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse(result);
    }
}
