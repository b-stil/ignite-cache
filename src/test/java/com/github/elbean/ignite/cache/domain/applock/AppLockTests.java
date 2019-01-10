package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.domain.applock.AppLock;
import org.junit.Assert;
import org.junit.Test;

public class AppLockTests {

    @Test
    public void getAppLockKeyReturnsCorrectKey() {
        AppLock lock = new AppLock();
        lock.setReferenceId(212);
        lock.setComponent("Tacos");

        Assert.assertEquals("212.Tacos", lock.getAppLockKey());
    }

    @Test
    public void validateAppLockEquals() {
        AppLock lock1 = new AppLock();
        lock1.setReferenceId(212);
        lock1.setComponent("Something");
        lock1.setLockOwner("TestOwner");

        AppLock lock2 = new AppLock();
        lock2.setReferenceId(212);
        lock2.setComponent("Something");
        lock2.setLockOwner("TestOwner");

        Assert.assertTrue(lock1.equals(lock2));
    }

    @Test
    public void validateAppLockNotEqualsOwner() {
        AppLock lock1 = new AppLock();
        lock1.setReferenceId(212);
        lock1.setComponent("Something");
        lock1.setLockOwner("Testowner");

        AppLock lock2 = new AppLock();
        lock2.setReferenceId(212);
        lock2.setComponent("Something");
        lock2.setLockOwner("TestOwner");

        Assert.assertFalse(lock1.equals(lock2));
    }

    @Test
    public void validateAppLockNotEqualsComponent() {
        AppLock lock1 = new AppLock();
        lock1.setReferenceId(212);
        lock1.setComponent("SomethingNew");
        lock1.setLockOwner("Testowner");

        AppLock lock2 = new AppLock();
        lock2.setReferenceId(212);
        lock2.setComponent("Something");
        lock2.setLockOwner("TestOwner");

        Assert.assertFalse(lock1.equals(lock2));
    }

    @Test
    public void validateAppLockNotEqualsReferenceId() {
        AppLock lock1 = new AppLock();
        lock1.setReferenceId(213);
        lock1.setComponent("Something");
        lock1.setLockOwner("Testowner");

        AppLock lock2 = new AppLock();
        lock2.setReferenceId(212);
        lock2.setComponent("Something");
        lock2.setLockOwner("TestOwner");

        Assert.assertFalse(lock1.equals(lock2));
    }
}
