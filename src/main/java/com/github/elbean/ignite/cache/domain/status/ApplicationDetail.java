/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.elbean.ignite.cache.domain.status;

/**
 *
 * @author BStilson
 */
public class ApplicationDetail {
    
    String applicationName;
    String applicationVersion;
    GridDetail gridDetail;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public GridDetail getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(GridDetail gridDetail) {
        this.gridDetail = gridDetail;
    }
    
    @Override
    public String toString(){
        return String.format("ApplicationDetail: [ applicationName='%s'; applicationVersion='%s'; gridDetail='%s' ]",
                this.getApplicationName(),
                this.getApplicationVersion(),
                ((this.getGridDetail() == null) ? "null" : this.getGridDetail().toString()));
    }
}
