package com.github.elbean.ignite.cache.infrastructure.service;

import com.github.elbean.ignite.cache.domain.applock.*;
import com.github.elbean.ignite.cache.domain.common.Specification;
import com.github.elbean.ignite.cache.domain.exceptions.AppLockAssignedToOtherOwnerException;
import com.github.elbean.ignite.cache.infrastructure.cache.AppLockCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppLockService {

    private static Logger LOGGER = LoggerFactory.getLogger(AppLockService.class);

    @Autowired
    public AppLockCache cache;

    public AppLock getExistingAppLock(AppLock lockToLookup) {
        LOGGER.trace("Retrieving existing AppLock with: '{}'", lockToLookup);
        AppLock existingLock = cache.get(lockToLookup.getAppLockKey());
        LOGGER.trace("Was existing lock found? '{}'", (existingLock != null));
        return existingLock;
    }

    public AppLock acquireAppLock(AppLock lockToAcquire) throws AppLockAssignedToOtherOwnerException {
        LOGGER.trace("Attempting to acquire AppLock with: '{}'", lockToAcquire);
        boolean appLockAcquired = false;
        ResourceLock resourceLock = null;
        try {
            resourceLock = cache.getResourceLock(lockToAcquire.getAppLockKey());
            if (resourceLock.isLocked()) {
                if (cache.isKeyInCache(lockToAcquire.getAppLockKey())) {
                    appLockAcquired = update(lockToAcquire);
                } else {
                    appLockAcquired = cache.put(lockToAcquire);
                }
            }
        } finally {
            if (resourceLock != null) {
                resourceLock.release();
            }
        }
        if (appLockAcquired) {
            LOGGER.trace("AppLock with: '{}' was acquired", lockToAcquire);
            return getExistingAppLock(lockToAcquire);
        }
        LOGGER.trace("Unable to acquire AppLock with: '{}'", lockToAcquire);
        return null;
    }

    public AppLock refresh(AppLock lockToRefresh) throws AppLockAssignedToOtherOwnerException {
        LOGGER.trace("Attempting to refresh AppLock with: '{}'", lockToRefresh);
        ResourceLock resourceLock = null;
        try {
            resourceLock = cache.getResourceLock(lockToRefresh.getAppLockKey());
            if (resourceLock.isLocked()) {
                if (!update(lockToRefresh)) {
                    LOGGER.trace("Unable to refresh AppLock with: '{}'", lockToRefresh);
                }
            }
        } finally {
            if (resourceLock != null) {
                resourceLock.release();
            }
        }
        AppLock appLock = getExistingAppLock(lockToRefresh);
        if (appLock != null) {
            LOGGER.trace("AppLock after attempted refresh: '{}'", appLock);
        } else {
            LOGGER.trace("AppLock with: '{}' was not able to be refreshed", lockToRefresh);
        }
        return appLock;
    }

    private boolean update(AppLock lockToUpdate) throws AppLockAssignedToOtherOwnerException {
        LOGGER.trace("Attempting to update AppLock with: '{}'", lockToUpdate);
        final Specification<AppLock> lockExists = new AppLockExistsRule();
        final Specification<AppLock> lockNotExpired = new AppLockIsNotExpiredRule();
        final Specification<AppLock> lockOwnedByRequester = new ExistingAppLockIsOwnedByRequesterRule(lockToUpdate);
        final Specification<AppLock> candidateValidation = lockExists.and(lockNotExpired).and(lockOwnedByRequester);

        AppLock existingLock = getExistingAppLock(lockToUpdate);
        LOGGER.trace("Executing rules for AppLock refresh criteria");
        if (!lockExists.isSatisfiedBy(existingLock)) {
            return false;
        }
        if (!lockOwnedByRequester.isSatisfiedBy(existingLock)) {
            if (lockNotExpired.isSatisfiedBy(existingLock)) {
                throw new AppLockAssignedToOtherOwnerException(String.format("App-Lock: '%s' is already owned by '%s'", existingLock.getAppLockKey(), existingLock.getLockOwner()));
            }
        }
        boolean wasUpdated = false;
        wasUpdated = cache.put(lockToUpdate);
        LOGGER.trace("Was AppLock with: '{}' updated? '{}'", lockToUpdate, wasUpdated);
        return wasUpdated;
    }

    public boolean release(AppLock toRelease) {
        LOGGER.trace("Attempting to release AppLock with: '{}'", toRelease);
        boolean isReleased = false;
        if ((isReleased = cache.remove(toRelease.getAppLockKey())) == true) {
            toRelease.expireAppLock();
        }
        LOGGER.trace("AppLock with: '{}' was released? '{}'", toRelease, toRelease.isExpired());
        return isReleased;
    }
}
