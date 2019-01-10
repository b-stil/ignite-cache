package com.github.elbean.ignite.cache.sharedKernel.exceptions;

public class CacheNotFoundException extends Exception {

    public CacheNotFoundException() {

    }

    public CacheNotFoundException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
