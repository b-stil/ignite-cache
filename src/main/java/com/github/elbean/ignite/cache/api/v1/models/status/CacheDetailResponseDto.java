/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.elbean.ignite.cache.api.v1.models.status;

/**
 * View model for details on a specific cache.
 * @author BStilson
 */
public class CacheDetailResponseDto {
    String cacheName;
    int entryCount;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    @Override
    public String toString(){
        return String.format("GridCacheResponseDto: [ cacheName='%s'; entryCount='%s' ]", 
                this.getCacheName(), 
                this.getEntryCount());
    }
}
