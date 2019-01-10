package com.github.elbean.ignite.cache.infrastructure.cache;

import com.github.elbean.ignite.cache.domain.applock.AppLock;
import com.github.elbean.ignite.cache.domain.applock.ResourceLock;
import com.github.elbean.ignite.cache.sharedKernel.exceptions.CacheNotFoundException;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppLockCache {

    private static Logger LOGGER = LoggerFactory.getLogger(AppLockCache.class);
    final String APP_LOCK_CACHE_NAME = "app-lock";
    private Ignite ignite;
    final IgniteCache<String, AppLock> appLocks;

    @Autowired
    public AppLockCache(Ignite ignite) throws CacheNotFoundException {
        LOGGER.info("Attempting to initialize cache: {}", APP_LOCK_CACHE_NAME);
        if (ignite == null) {
            LOGGER.warn("Cache fabric is not properly initialized for cache: {}", APP_LOCK_CACHE_NAME);
            throw new CacheNotFoundException("Cache fabric is not initialized");
        }
        this.ignite = ignite;
        appLocks = this.ignite.getOrCreateCache(APP_LOCK_CACHE_NAME);
        if (appLocks == null) {
            throw new CacheNotFoundException(String.format("Cache: %s was not found", APP_LOCK_CACHE_NAME));
        }
        LOGGER.info("Cache: '{}' was successfully initialized", APP_LOCK_CACHE_NAME);
    }

    public AppLock get(String key) {
        LOGGER.trace("Attempting to get key: '{}' from cache: '{}'", key, APP_LOCK_CACHE_NAME);
        AppLock cacheEntry = appLocks.get(key);
        LOGGER.trace("Cache item with key: '{}' was found?: '{}'", key, (cacheEntry != null));
        return cacheEntry;
    }

    public ResourceLock getResourceLock(String lockKey) {
        LOGGER.trace("Acquiring lock using key: '{}'", lockKey);
        ResourceLock resourceLock = new ResourceLock(appLocks.lock(lockKey));
        if (resourceLock.acquire()) {
            LOGGER.trace("Resource lock using key: '{}' was acquired", lockKey);
        } else {
            LOGGER.trace("Unable to acquire resource lock using key: '{}'", lockKey);
        }
        return resourceLock;
    }

    public boolean put(AppLock toUpdate) {
        LOGGER.trace("Attempting to update AppLock with: {}", toUpdate);
        boolean wasUpdated;
        try {
            appLocks.put(toUpdate.getAppLockKey(), toUpdate);
            wasUpdated = true;
        } catch (Exception ex) {
            LOGGER.error("Issue attempting to update AppLock: {} with error meesage: {}", toUpdate, ex.getMessage());
            wasUpdated = false;
        }
        LOGGER.trace("AppLock with: '{}' was updated?: '{}'", toUpdate, wasUpdated);
        return wasUpdated;
    }

    public boolean remove(String key) {
        LOGGER.trace("Attempting to remove AppLock with key: '{}'", key);
        boolean wasRemoved = true;
        if (isKeyInCache(key)) {
            wasRemoved = appLocks.remove(key);
        }
        LOGGER.trace("AppLock with key: '{}' was successfully removed?: '{}'", key, wasRemoved);
        return wasRemoved;
    }

    public boolean isKeyInCache(String key) {
        LOGGER.trace("Checking to see if AppLock with key: '{}' is in cache: '{}'", key, APP_LOCK_CACHE_NAME);
        boolean exists;
        exists = appLocks.containsKey(key);
        LOGGER.trace("AppLock with key: '{}' exists?: '{}'", key, exists);
        return exists;
    }
}
