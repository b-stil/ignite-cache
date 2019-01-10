/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.elbean.ignite.cache.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author BStilson
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartup.class);
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("Service running");
    }
    
}
