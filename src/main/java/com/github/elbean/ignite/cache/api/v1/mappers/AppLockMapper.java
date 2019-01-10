package com.github.elbean.ignite.cache.api.v1.mappers;

import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockDto;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockQueryDto;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockResponseDto;
import com.github.elbean.ignite.cache.domain.applock.AppLock;

/**
 * Maps between View Models and Domain Models.
 * @author BStilson
 */
public class AppLockMapper {

    public static AppLock toAppLock(AppLockQueryDto dto) {
        AppLock appLock = new AppLock();
        appLock.setReferenceId(dto.getReferenceId());
        appLock.setComponent(dto.getComponent());
        appLock.setLockOwner(dto.getOwner());
        return appLock;
    }

    public static AppLock toAppLock(AppLockDto dto) {
        AppLock appLock = new AppLock();
        appLock.setReferenceId(dto.getReferenceId());
        appLock.setComponent(dto.getComponent());
        appLock.setLockOwner(dto.getOwner());
        appLock.setLockedDT(dto.getStartDT());
        appLock.setExpirationDT(dto.getDuration(), dto.getUnitOfDurationAsDateUnit());
        return appLock;
    }

    public static AppLockResponseDto toAppLockResponse(AppLock appLock) {
        AppLockResponseDto dto = new AppLockResponseDto();
        dto.setReferenceId(appLock.getReferenceId());
        dto.setOwner(appLock.getLockOwner());
        dto.setComponent(appLock.getComponent());
        dto.setExpirationDT(appLock.getExpirationDT());
        return dto;
    }
}
