package com.github.elbean.ignite.cache.domain.applock;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class ResourceLock {

    private static Logger LOGGER = LoggerFactory.getLogger(ResourceLock.class);

    public ResourceLock(Lock lock) {
        this.lock = lock;
    }
    private boolean isLocked = false;

    private Lock lock = null;

    public boolean isLocked() {
        return isLocked;
    }

    public boolean acquire() {
        LOGGER.trace("Attempting to acquire resource lock");
        try {
            this.isLocked = this.lock.tryLock(1, TimeUnit.SECONDS);
            return isLocked();
        } catch (InterruptedException e) {
            LOGGER.error("Unable to acquire resource lock");
            return false;
        }
    }

    public void release() {
        LOGGER.trace("Releasing resource lock");
        this.lock.unlock();
    }
}
