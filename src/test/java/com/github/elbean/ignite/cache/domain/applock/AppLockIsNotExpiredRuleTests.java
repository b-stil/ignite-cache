package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.domain.applock.AppLock;
import com.github.elbean.ignite.cache.domain.applock.AppLockExistsRule;
import com.github.elbean.ignite.cache.domain.applock.AppLockIsNotExpiredRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

public class AppLockIsNotExpiredRuleTests {

    @Test
    public void validateThatIfExpirationIsInFutureRuleReturnsTrue() {
        AppLock lock = new AppLock();
        lock.setReferenceId(1);
        lock.setComponent("banana");
        lock.setLockOwner("Test");
        lock.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        lock.setExpirationDT(5, DateUnit.MINUTES);

        AppLockIsNotExpiredRule rule = new AppLockIsNotExpiredRule();
        boolean result = rule.isSatisfiedBy(lock);
        Assert.assertTrue(result);
    }

    @Test
    public void validateThatIfExpirationDateHasElapsedRuleReturnsFalse() {
        AppLock lock = new AppLock();
        lock.setReferenceId(1);
        lock.setComponent("banana");
        lock.setLockOwner("Test");
        lock.setLockedDT(new DateTime(DateTimeZone.UTC).minusMinutes(2));
        lock.setExpirationDT(-5, DateUnit.MINUTES);

        AppLockIsNotExpiredRule rule = new AppLockIsNotExpiredRule();
        boolean result = rule.isSatisfiedBy(lock);
        Assert.assertFalse(result);
    }
}
