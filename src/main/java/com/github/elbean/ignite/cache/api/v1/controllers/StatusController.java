package com.github.elbean.ignite.cache.api.v1.controllers;

import com.github.elbean.ignite.cache.api.v1.mappers.StatusResponseMapper;
import com.github.elbean.ignite.cache.api.v1.models.status.StatusResponseDto;
import com.github.elbean.ignite.cache.domain.status.ApplicationDetail;
import com.github.elbean.ignite.cache.infrastructure.service.GridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("status")
@Api(value = "Status Version 1", produces = "application/json")
public class StatusController {

    private static Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

    private GridService gridService;

    @Autowired
    public StatusController(GridService gridService) {
        this.gridService = gridService;
    }

    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get a 'pong' response if the service is up.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response ping() {
        LOGGER.trace("GET/ping called");
        return Response.status(Response.Status.OK).entity("{\"message\":\"pong\"}").build();
    }

    @GET
    @Path("/details")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get details of the currently running service instance with basic cache metrics.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = StatusResponseDto.class),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response details() {
        LOGGER.trace("GET/details called");
        ApplicationDetail detail = gridService.getApplicationDetails();
        StatusResponseDto result = StatusResponseMapper.toStatusResponseDto(detail);
        LOGGER.trace("GET/details result: {}", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
