package com.github.elbean.ignite.cache.domain.applock;

import org.junit.Assert;
import org.junit.Test;

public class AppLockExistRuleTests {

    @Test
    public void validateThatNullLockDoesNotExist() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = null;
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse("Expected null AppLock to return false", result);
    }

    @Test
    public void validateThatAppLockThatWasOnlyInstantiatedDoesNotExist() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = new AppLock();
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse("Expected empty AppLock to return false since the key cannot be generated", result);
    }

    @Test
    public void validateThatAppLockDoesNotExistWhenReferenceIdIsZero() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = new AppLock();
        candidate.setReferenceId(0);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse("Expected AppLock with referenceId of zero to return false since that is the default int value", result);
    }

    @Test
    public void validateThatAppLockDoesNotExistWhenComponentIsNull() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = new AppLock();
        candidate.setReferenceId(5);
        candidate.setComponent(null);
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse("Expected null component for AppLock to return false", result);
    }

    @Test
    public void validateThatAppLockDoesNotExistWhenComponentIsEmpty() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = new AppLock();
        candidate.setReferenceId(5);
        candidate.setComponent("");
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertFalse("Expected empty component for AppLock to return false", result);
    }

    @Test
    public void validateThatAppLockExistsWhenCorrectlyInstantiated() {
        AppLockExistsRule rule = new AppLockExistsRule();
        AppLock candidate = new AppLock();
        candidate.setReferenceId(5);
        candidate.setComponent("TestComponent");
        boolean result = rule.isSatisfiedBy(candidate);
        Assert.assertTrue("Expected legitimate AppLock to return true", result);
    }
}
