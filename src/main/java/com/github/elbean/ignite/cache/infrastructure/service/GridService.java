package com.github.elbean.ignite.cache.infrastructure.service;

import com.github.elbean.ignite.cache.domain.status.ApplicationDetail;
import com.github.elbean.ignite.cache.domain.status.GridCache;
import com.github.elbean.ignite.cache.domain.status.GridDetail;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GridService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GridService.class);

    private Ignite ignite;
    private final String appName;
    private final String appVersion;

    /**
     *
     * @param ignite
     * @param appName
     * @param appVersion
     */
    @Autowired
    public GridService(
            Ignite ignite,
            @Value("${info.build.name}")String appName, 
            @Value("${info.build.version}")String appVersion) {
        this.ignite = ignite;
        this.appName = appName;
        this.appVersion = appVersion;
    }

    public ApplicationDetail getApplicationDetails(){
        LOGGER.trace("Getting application details");
        ApplicationDetail ad = new ApplicationDetail();
        ad.setApplicationName(this.appName);
        ad.setApplicationVersion(this.appVersion);
        ad.setGridDetail(getGridDetails());
        LOGGER.trace("Application details: {}", ad);
        return ad;
    }
    
    public GridDetail getGridDetails() {
        LOGGER.trace("Getting details and metrics for grid");
        GridDetail detail = new GridDetail();
        detail.setVersionNumber(ignite.version().toString());
        detail.setActive(ignite.active());
        detail.setGridInstanceName(ignite.name());
        detail.setCaches(getCachesDetails());
        LOGGER.trace("Grid details: {}", detail);
        return detail;
    }
    
    public Collection<GridCache> getCachesDetails(){
        LOGGER.trace("Gathering metrics for caches");
        Collection<GridCache> gridCaches = new ArrayList<>();
        Collection<String> cacheNames = ignite.cacheNames();
        for(String c : cacheNames){
            IgniteCache cache = ignite.cache(c);
            CacheMetrics metrics = cache.metrics();
            GridCache gc = new GridCache();
            gc.setCacheName(c);   
            gc.setEntryCount(metrics.getKeySize());
            gridCaches.add(gc);
        }
        LOGGER.trace("Number of caches found: {}", gridCaches.size());
        return gridCaches;
    }
}
