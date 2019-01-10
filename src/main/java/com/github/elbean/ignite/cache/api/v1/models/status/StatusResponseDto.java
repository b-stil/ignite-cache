package com.github.elbean.ignite.cache.api.v1.models.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elbean.ignite.cache.api.models.result.ResultObjectName;

/**
 * View model for status responses.
 * @author BStilson
 */
@ResultObjectName("status")
public class StatusResponseDto {

    String version;
    
    String application;
    
    @JsonProperty(value = "grid")
    GridResponseDto gridResponseDto;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationName() {
        return application;
    }

    public void setApplicationName(String applicationName) {
        this.application = applicationName;
    }

    public GridResponseDto getGridResponseDto() {
        return gridResponseDto;
    }

    public void setGridResponseDto(GridResponseDto gridResponseDto) {
        this.gridResponseDto = gridResponseDto;
    }

    @Override
    public String toString(){
        return String.format("StatusResponseDto: [ appName='%s'; version='%s'; gridResponse='%s' ]",
                this.getApplicationName(),
                this.getVersion(),
                ((this.getGridResponseDto() == null) ? "null" : this.getGridResponseDto()));
    }
}
