/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.elbean.ignite.cache.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author BStilson
 */
public class ApplicationShutdown implements ApplicationListener<ExitCodeEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationShutdown.class);
    
    @Override
    public void onApplicationEvent(ExitCodeEvent event) {
        LOGGER.info("Service shutting down with exit code: '{}", event.getExitCode());
    }
    
}
