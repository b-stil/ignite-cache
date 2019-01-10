package com.github.elbean.ignite.cache.api.security;

import com.github.elbean.ignite.cache.sharedKernel.helpers.StringHelpers;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Base64;

public class RequestMessageHelpers {

    /**
     * Parse the Authorization Header to get the credentials. Expecting at least
     * part of the Authorization Header to be base64 encoded.
     *
     * @param authHeader
     * @return username in position 0 and crypto signature in position 1
     */
    public static String[] ParseAuthHeader(String authHeader) {
        final String authScheme = "user ";
        if (authHeader == null || authHeader.isEmpty()) {
            return null;
        }

        //check to see if the auth header was all base 64 encoded
        if (!authHeader.startsWith(authScheme)) {
            authHeader = decodeAuthHeader(authHeader);
        }
        //remove the scheme from the header
        authHeader = authHeader.replaceFirst("[U|u]ser ", "");

        //check to see if what remains is still base64 encoded
        authHeader = decodeAuthHeader(authHeader);

        String[] credentials = authHeader.split(":", 2);
        if (credentials.length != 2
                || credentials[0] == null
                || credentials[0].isEmpty()
                || credentials[1] == null
                || credentials[1].isEmpty()) {
            return null;
        }

        return credentials;
    }

    /**
     * Wrapper to base 64 decode. If cannot be decoded returns what came in or
     * null.
     *
     * @param input
     * @return
     */
    public static String decodeAuthHeader(String input) {
        Base64.Decoder decoder = Base64.getDecoder();
        String result = null;
        try {
            byte[] decoded = decoder.decode(input);
            result = new String(decoded);
        } catch (IllegalArgumentException i) {
            //log that it wasn't base 64 at some point
        } finally {
            return (result == null && input != null) ? input : result;
        }
    }

    /**
     * Get the string that will be used to generate the signature for the
     * request.
     *
     * @param uri
     * @param content
     * @return
     */
    public static String generateSignatureContent(UriInfo uri, String content) {
        StringBuilder signatureContent = new StringBuilder();
        URI requestUri = uri.getRequestUri();
        signatureContent.append(requestUri.getPath());
        String query = requestUri.getQuery();
        if (query != null && !query.isEmpty()) {
            signatureContent.append("?").append(query);
        }
        if (content != null && !content.isEmpty()) {
            if (signatureContent.indexOf("?") > -1) {
                signatureContent.append("&");
            } else {
                signatureContent.append("?");
            }
            signatureContent.append(StringHelpers.removeWhiteSpace(content));
        }
        return signatureContent.toString();
    }
}
