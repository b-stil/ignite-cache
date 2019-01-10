package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.domain.common.AbstractSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLockExistsRule extends AbstractSpecification<AppLock> {

    private static Logger LOGGER = LoggerFactory.getLogger(AppLockExistsRule.class);

    @Override
    public boolean isSatisfiedBy(AppLock candidate) {
        if (candidate == null) {
            LOGGER.trace("Candidate is null");
            return false;
        }
        if (candidate.getReferenceId() <= 0
                || candidate.getComponent() == null
                || candidate.getComponent().isEmpty()) {
            LOGGER.trace("Candidate does not have a valid lock key");
            return false;
        }
        LOGGER.trace("Candidate is configured as an acceptable lock but may not be valid");
        return true;
    }
}
