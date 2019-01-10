package com.github.elbean.ignite.cache.api.v1.models.applock;

import com.github.elbean.ignite.cache.api.models.result.ResultObjectName;

import org.joda.time.DateTime;

/**
 * View Model response for app-lock actions.
 * @author BStilson
 */
@ResultObjectName(value = "appLock")
public class AppLockResponseDto {

    int referenceId;

    String owner;

    String component;

    DateTime expirationDT;

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setExpirationDT(DateTime expirationDT) {
        this.expirationDT = expirationDT;
    }

    public int getReferenceId() {
        if (referenceId > 0) {
            return this.referenceId;
        } else {
            return -1;
        }
    }

    public String getOwner() {
        return owner;
    }

    public String getComponent() {
        return component;
    }

    public DateTime getExpirationDT() {
        return expirationDT;
    }

    @Override
    public String toString() {
        return String.format("AppLockDto: [referenceId='%s'; owner='%s'; component='%s'; expirationDT='%s']",
                getReferenceId(), getOwner(), getComponent(), getExpirationDT().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}


