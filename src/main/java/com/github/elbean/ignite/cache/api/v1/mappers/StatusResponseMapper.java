package com.github.elbean.ignite.cache.api.v1.mappers;

import com.github.elbean.ignite.cache.api.v1.models.status.CacheDetailResponseDto;
import com.github.elbean.ignite.cache.api.v1.models.status.GridResponseDto;
import com.github.elbean.ignite.cache.api.v1.models.status.StatusResponseDto;
import com.github.elbean.ignite.cache.domain.status.ApplicationDetail;
import com.github.elbean.ignite.cache.domain.status.GridCache;
import com.github.elbean.ignite.cache.domain.status.GridDetail;

/**
 * Maps between View Models and Domain Models.
 * @author BStilson
 */
public class StatusResponseMapper {
 
    public static StatusResponseDto toStatusResponseDto(ApplicationDetail appDetail){
        StatusResponseDto dto = new StatusResponseDto();
        dto.setApplicationName(appDetail.getApplicationName());
        dto.setVersion(appDetail.getApplicationVersion());
        dto.setGridResponseDto(toGridResponseDto(appDetail.getGridDetail()));
        return dto;
    }
    
    public static GridResponseDto toGridResponseDto(GridDetail gridDetail){
        GridResponseDto dto = new GridResponseDto();
        dto.setGridName(gridDetail.getGridInstanceName());
        dto.setGridVersion(gridDetail.getVersionNumber());
        dto.setIsActive(gridDetail.isActive());
        gridDetail.getCaches().forEach((c) -> {
            dto.addGridCache(toCacheDetailResponseDto(c));
        });
        return dto;
    }
    
    public static CacheDetailResponseDto toCacheDetailResponseDto(GridCache gridCache){
        CacheDetailResponseDto dto = new CacheDetailResponseDto();
        dto.setCacheName(gridCache.getCacheName());
        dto.setEntryCount(gridCache.getEntryCount());
        return dto;
    }
}
