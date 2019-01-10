package com.github.elbean.ignite.cache.api.v1.controllers;

import com.github.elbean.ignite.cache.api.models.result.ErrorResult;
import com.github.elbean.ignite.cache.api.v1.mappers.AppLockMapper;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockQueryDto;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockResponseDto;
import com.github.elbean.ignite.cache.domain.applock.AppLock;
import com.github.elbean.ignite.cache.infrastructure.service.AppLockService;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockDto;
import com.github.elbean.ignite.cache.domain.exceptions.AppLockAssignedToOtherOwnerException;
import com.github.elbean.ignite.cache.sharedKernel.constants.Errors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("app-lock")
@Api(value = "AppLock Version 1", produces = "application/json")
public class AppLockController {

    private static Logger LOGGER = LoggerFactory.getLogger(AppLockController.class);

    private AppLockService _service;

    @Autowired
    public AppLockController(AppLockService service) {
        _service = service;
    }

    @GET
//    @ApiAuthorization
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve an existing app-lock.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppLockResponseDto.class),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 400, message = "Bad Request")
    })
    public Response getCurrentAppLockDetails(@Valid @BeanParam AppLockQueryDto dto) {
        LOGGER.trace("GET with: '{}'", dto);
        AppLock appLock = AppLockMapper.toAppLock(dto);
        appLock = _service.getExistingAppLock(appLock);

        if (appLock == null) {
            ErrorResult er = new ErrorResult(true, Errors.AppLockMessages.APP_LOCK_NOT_FOUND);
            LOGGER.trace("GET: result: '{}'", er);
            return Response.status(Response.Status.NOT_FOUND).entity(er).build();
        }
        AppLockResponseDto result = AppLockMapper.toAppLockResponse(appLock);
        LOGGER.trace("GET: result: '{}'", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    //TODO: Need to account for other user holding the lock.
    @POST
//    @ApiAuthorization
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Acquire an app-lock for a given component")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppLockResponseDto.class),
        @ApiResponse(code = 409, message = "App-Lock not updatable"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response acquireAppLock(@Valid AppLockDto dto) {
        LOGGER.trace("POST with: '{}'", dto);
        AppLock appLock;
        try {
            appLock = _service.acquireAppLock(AppLockMapper.toAppLock(dto));
        } catch (AppLockAssignedToOtherOwnerException ex) {
            ErrorResult er = new ErrorResult(true, ex.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(er).build();
        }
        if (appLock == null) {
            ErrorResult er = new ErrorResult(true,
                    String.format("App-Lock for referenceId: '%s'; component: '%s'; owner: '%s'; was not acquired", dto.getReferenceId(), dto.getComponent(), dto.getOwner()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(er).build();
        }
        AppLockResponseDto result = AppLockMapper.toAppLockResponse(appLock);
        LOGGER.trace("POST result: '{}'", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @PUT
//    @ApiAuthorization
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update an existing app-lock")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppLockResponseDto.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 409, message = "App-Lock not updatable")
    })
    public Response updateAppLock(@Valid AppLockDto dto) {
        LOGGER.trace("PUT with: '{}'", dto);
        AppLock appLock;
        try {
            appLock = _service.refresh(AppLockMapper.toAppLock(dto));
        } catch (AppLockAssignedToOtherOwnerException ex) {
            ErrorResult er = new ErrorResult(true, ex.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(er).build();
        }
        if(appLock == null){
            ErrorResult er = new ErrorResult(true, "Cannot update an App-Lock that does not already exist.");
            return Response.status(Response.Status.NOT_FOUND).entity(er).build();
        }
        AppLockResponseDto result = AppLockMapper.toAppLockResponse(appLock);
        LOGGER.trace("PUT result: '{}'", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
//    @ApiAuthorization
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Release an existing app-lock")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Released"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 410, message = "Already released")
    })
    public Response removeAppLock(@Valid @BeanParam AppLockQueryDto dto) {
        LOGGER.trace("DELETE with: '{}'", dto);
        boolean wasReleased = _service.release(AppLockMapper.toAppLock(dto));
        if (!wasReleased) {
            return Response.status(Response.Status.GONE).build();
        }
        LOGGER.trace("DELETE result: '{}'", wasReleased);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
