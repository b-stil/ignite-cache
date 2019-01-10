package com.github.elbean.ignite.cache.api.security;

import org.glassfish.jersey.message.internal.ReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApiAuthorization
public class ApiAuthorizationFilter implements ContainerRequestFilter {

    @Autowired
    public ApiKeys apiKeys;

    @Override
    public void filter(ContainerRequestContext requestContext) throws WebApplicationException {
        if (requestContext == null) {
            throw new IllegalArgumentException("requestContext");
        }

        UriInfo uri = requestContext.getUriInfo();
        String scheme = requestContext.getUriInfo().getRequestUri().getScheme();

        //validate that it is https
//        if(!scheme.toLowerCase().equals("https")){
//            throw new WebApplicationException(Response.Status.BAD_REQUEST);
//        }
        //validate the auth header
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String[] credentials = RequestMessageHelpers.ParseAuthHeader(authHeader);
        if (credentials == null) {
            throw new NotAuthorizedException("Invalid Authorization parameters");
        }
        //validate request signature
        String content = getRequestContent(requestContext);
        if (!requestHasValidSignature(uri, content, credentials[0], credentials[1])) {
            throw new NotAuthorizedException("Unauthorized");
        }
    }

    public String getRequestContent(ContainerRequestContext requestContext) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final InputStream inputStream = requestContext.getEntityStream();
        final StringBuilder sb = new StringBuilder();
        try {
            ReaderWriter.writeTo(inputStream, outStream);
            byte[] requestEntity = outStream.toByteArray();
            if (requestEntity.length == 0) {
                sb.append("");
            } else {
                sb.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
        } catch (IOException ex) {
            //blah
        }
        return sb.toString();
    }

    public boolean requestHasValidSignature(UriInfo uri, String requestContent, String username, String expectedSignature) {
        String signatureContent = RequestMessageHelpers.generateSignatureContent(uri, requestContent);
        String privateKey = apiKeys.getApiKey(username);
        return HmacSignatureHelpers.validateExpectedSignature(signatureContent, privateKey, expectedSignature);
    }
}
