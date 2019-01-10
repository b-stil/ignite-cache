package com.github.elbean.ignite.cache.domain.applock;

import com.github.elbean.ignite.cache.api.models.DateUnit;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Objects;

public class AppLock {

    int referenceId;
    String lockOwner;
    String component;
    DateTime lockedDT;
    DateTime expirationDT;

    //region getter/setter
    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public DateTime getLockedDT() {
        return lockedDT;
    }

    public void setLockedDT(DateTime lockedDT) {
        if (lockedDT == null) {
            this.lockedDT = DateTime.now(DateTimeZone.UTC);
        } else {
            this.lockedDT = lockedDT;
        }
    }

    public DateTime getExpirationDT() {
        return expirationDT;
    }

    public String getExpirationDT(String pattern) {
        if (expirationDT != null) {
            return expirationDT.toString(pattern);
        }
        return "";
    }

    public void setExpirationDT(int duration, DateUnit unitOfDuration) {
        switch (unitOfDuration) {
            case SECONDS:
                this.expirationDT = this.getLockedDT().plusSeconds(duration);
                break;
            case MINUTES:
                this.expirationDT = this.getLockedDT().plusMinutes(duration);
                break;
            case HOURS:
                this.expirationDT = this.getLockedDT().plusHours(duration);
                break;
            default:
                this.expirationDT = this.getLockedDT();
        }
    }

    //endregion
    public void expireAppLock() {
        this.expirationDT = DateTime.now(DateTimeZone.UTC).minusMinutes(1);
    }

    public String getAppLockKey() {
        return this.getReferenceId() + "." + this.getComponent();
    }

    public boolean isExpired() {
        return this.expirationDT.isBefore(DateTime.now(DateTimeZone.UTC));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AppLock)) {
            return false;
        }

        final AppLock other = (AppLock) obj;
        if (Objects.equals(this.getAppLockKey(), other.getAppLockKey()) && (Objects.equals(this.getLockOwner(), other.getLockOwner()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAppLockKey(), this.getLockOwner());
    }

    @Override
    public String toString() {
        return String.format("AppLock:[ key='%s'; expirationDT='%s' ]", getAppLockKey(), (this.getExpirationDT() != null) ? getExpirationDT().toString("yyyy-MM-dd HH:mm:ss.SSS") : "");
    }
}
