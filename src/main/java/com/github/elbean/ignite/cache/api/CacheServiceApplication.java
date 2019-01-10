package com.github.elbean.ignite.cache.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan({"com.github.elbean.ignite.cache"})
@ImportResource("classpath:igniteBean.xml")
public class CacheServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceApplication.class);
    
    public static void main(String[] args) {
        LOGGER.info("Starting service...");
        SpringApplication app = new SpringApplication(CacheServiceApplication.class);
        app.run(args);
    }
}
