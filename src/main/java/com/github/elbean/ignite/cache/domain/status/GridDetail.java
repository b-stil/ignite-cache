package com.github.elbean.ignite.cache.domain.status;

import java.util.ArrayList;
import java.util.Collection;

public class GridDetail {

    String gridInstanceName;
    String gridVersionNumber;
    Collection<GridCache> caches;
    boolean isActive;

    public GridDetail() {
        caches = new ArrayList<>();
    }
    
    public String getGridInstanceName() {
        return gridInstanceName;
    }

    public void setGridInstanceName(String instanceName) {
        this.gridInstanceName = instanceName;
    }

    public String getVersionNumber() {
        return gridVersionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.gridVersionNumber = versionNumber;
    }

    public Collection<GridCache> getCaches() {
        return caches;
    }

    public void setCaches(Collection<GridCache> caches) {
        this.caches = caches;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString(){
        return String.format("GridDetail: [ gridInstanceName='%s'; gridVersionNumber='%s'; isActive='%s'; caches=[ %s ] ]",
                this.getGridInstanceName(),
                this.getVersionNumber(),
                this.isActive(),
                ((this.getCaches() == null) ? "null" : this.getCaches().toString())
        );
    }
}
