package com.github.elbean.ignite.cache.api.v1.models.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;

/**
 * View model for details on the in memory data grid.
 * @author BStilson
 */
public class GridResponseDto {

    String gridName;
    
    String gridVersion;
    
    @JsonProperty(value = "caches")
    Collection<CacheDetailResponseDto> caches;
    
    boolean isActive;

    public GridResponseDto() {
        caches = new ArrayList<>();
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getGridVersion() {
        return gridVersion;
    }

    public void setGridVersion(String gridVersion) {
        this.gridVersion = gridVersion;
    }

    public Collection<CacheDetailResponseDto> getCaches() {
        return caches;
    }

    public void addGridCache(CacheDetailResponseDto dto) {
        this.caches.add(dto);
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString(){
        return String.format("GridDetailResponseDto: [ gridName='%s'; gridVersion='%s'; caches=[ %s ] ]",
                this.getGridName(),
                this.getGridVersion(),
                this.getCaches().toString());
    }
}
