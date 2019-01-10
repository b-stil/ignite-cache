package com.github.elbean.ignite.cache.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.elbean.ignite.cache.api.models.validation.exceptionmappers.ConstraintViolationExceptionMapper;
import com.github.elbean.ignite.cache.api.security.ApiAuthorizationFilter;
import com.github.elbean.ignite.cache.api.v1.controllers.AppLockController;
import com.github.elbean.ignite.cache.api.v1.controllers.StatusController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

@Component
@ApplicationPath("api/v1")
public class JerseyConfig extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyConfig.class);
    
    final String SCAN_PACKAGE_V1 = "com.github.elbean.ignite.cache.api.v1.controllers";
    final String version;
    final ObjectMapper om;
    ApplicationContext appCtx;
    
    @Autowired
    public JerseyConfig(ObjectMapper objectMapper, @Value("${info.build.version}") String versionNumber, ApplicationContext appContext) {
        LOGGER.info("Configuring Jersey...");
        this.version = versionNumber;
        this.om = objectMapper;
        this.appCtx = appContext;
        this.registerEndpoints();
    }

    private void registerEndpoints() {
//        packages(SCAN_PACKAGE_V1);
        this.register(StatusController.class);
        this.register(AppLockController.class);
        this.register(ApiAuthorizationFilter.class);
        this.register(new ObjectMapperContextResolver(om));
        this.register(ConstraintViolationExceptionMapper.class);
        this.register(WadlResource.class);
    }

    @PostConstruct
    public void init() {
//        LOGGER.info("Rest classes found:");
//        Map<String,Object> beans = appCtx.getBeansWithAnnotation(Path.class);
//        for (Object o : beans.values()) {
//            LOGGER.info(" -> " + o.getClass().getName());
//            register(o);
//        }
        this.configureSwagger();
        LOGGER.info("Jersey configuration complete!");
    }

    private void configureSwagger() {
        LOGGER.info("Configuring API documentation...");
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("v1");
        config.setTitle("Ignite Cache Service ");
        config.setVersion("1.0");
        config.setSchemes(new String[]{"http", "https"});
        config.setHost("localhost:52215");
        config.setBasePath("/api/v1");
        config.setPrettyPrint(true);
        config.setResourcePackage(SCAN_PACKAGE_V1);
        config.setScan(true);
    }
    
        @Provider
    public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        public ObjectMapperContextResolver(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }
    }
}
