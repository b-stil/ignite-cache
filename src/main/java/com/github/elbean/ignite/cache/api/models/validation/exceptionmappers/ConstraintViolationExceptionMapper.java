package com.github.elbean.ignite.cache.api.models.validation.exceptionmappers;

import com.github.elbean.ignite.cache.api.models.result.ValidationErrorResult;

import java.util.Set;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.hibernate.validator.internal.engine.path.PathImpl;

/**
 * Intercepts ConstraintViolationExceptions (Validation) to pretty print the
 * validation errors in a meaningful way.
 *
 * @author BStilson
 */
@Singleton
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationErrorResult response = new ValidationErrorResult();
        response.setPath(uri.getRequestUri().getPath());
        response.setError(true);
        response.setErrorMessage(exception.getMessage());
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        if (!violations.isEmpty()) {
            violations.forEach((ConstraintViolation<?> v) -> {
                response.addValidationError(((PathImpl) v.getPropertyPath()).getLeafNode().getName(), v.getMessage());                
            });
        }
        return Response.status(Status.BAD_REQUEST).entity(response).build();
    }

}
