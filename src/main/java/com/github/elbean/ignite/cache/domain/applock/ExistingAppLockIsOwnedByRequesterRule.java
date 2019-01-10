package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.domain.common.AbstractSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExistingAppLockIsOwnedByRequesterRule extends AbstractSpecification<AppLock> {

    private static Logger LOGGER = LoggerFactory.getLogger(ExistingAppLockIsOwnedByRequesterRule.class);

    private AppLock candidate;

    public ExistingAppLockIsOwnedByRequesterRule(AppLock candidate) {
        this.candidate = candidate;
    }

    @Override
    public boolean isSatisfiedBy(AppLock existingLock) {
        if (existingLock == null) {
            LOGGER.trace("Existing lock is null so it cannot be owned by the requester");
            return false;
        }
        if (candidate == null) {
            LOGGER.trace("Candidate lock is null so it cannot be evaluated against the existing lock");
            return false;
        }
        if (candidate.equals(existingLock)) {
            LOGGER.trace("Candidate and Existing lock are both owned by the requester");
            return true;
        }
        LOGGER.trace("Candidate: '{}' or Existing: '{}' lock are not owned by the same requester", candidate.toString(), existingLock.toString());
        return false;
    }
}
