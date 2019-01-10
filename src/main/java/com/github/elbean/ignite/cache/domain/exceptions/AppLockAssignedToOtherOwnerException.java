package com.github.elbean.ignite.cache.domain.exceptions;

/**
 * Exception to be used to signal that the requested app-lock is held by another owner.
 * @author BStilson
 */
public class AppLockAssignedToOtherOwnerException extends Exception {

    public AppLockAssignedToOtherOwnerException(String message) {
        super(message);
    }
}
