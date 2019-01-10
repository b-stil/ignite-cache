package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.domain.common.AbstractSpecification;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLockIsNotExpiredRule extends AbstractSpecification<AppLock> {

    private static Logger LOGGER = LoggerFactory.getLogger(AppLockIsNotExpiredRule.class);

    @Override
    public boolean isSatisfiedBy(AppLock candidate) {
        if (candidate == null) {
            LOGGER.trace("Candidate is null, cannot evaluate as an existing AppLock");
            return false;
        }

        LOGGER.trace("Candidate: '{}'", candidate.toString());
        DateTime currentDT = new DateTime(DateTimeZone.UTC);
        if (currentDT.isBefore(candidate.expirationDT)) {
            LOGGER.trace("Candidate is not expired");
            return true;
        }
        LOGGER.trace("Candidate expired: '{}'; current time evaluated: '{}'", candidate.getExpirationDT("yyyy-MM-dd HH:mm:ss.SSS"), currentDT.toString("yyyy-MM-dd HH:mm:ss.SSS"));
        return false;
    }
}
