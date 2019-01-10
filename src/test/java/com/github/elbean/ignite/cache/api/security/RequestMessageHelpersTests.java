package com.github.elbean.ignite.cache.api.security;

import com.github.elbean.ignite.cache.api.security.RequestMessageHelpers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class RequestMessageHelpersTests {

    @Test
    public void generateSignatureContentFromJsonWithUrlParams() {
        String content = "{ \"tortilla\": \"joe\" }";
        UriInfo uri = Mockito.mock(UriInfo.class);
        Mockito.when(uri.getRequestUri()).thenReturn(URI.create("http://localhost:8080/v1/test?with=param"));
        String result = RequestMessageHelpers.generateSignatureContent(uri, content);
        Assert.assertEquals("/v1/test?with=param&{\"tortilla\":\"joe\"}", result);
    }

    @Test
    public void generateSignatureContentFromJsonWithoutUrlParams() {
        String content = "{ \"tortilla\": \"joe\" }";
        UriInfo uri = Mockito.mock(UriInfo.class);
        Mockito.when(uri.getRequestUri()).thenReturn(URI.create("http://localhost:8080/v1/test"));
        String result = RequestMessageHelpers.generateSignatureContent(uri, content);
        Assert.assertEquals("/v1/test?{\"tortilla\":\"joe\"}", result);
    }

    @Test
    public void generateSignatureContentFromXmlWithUrlParams() {
        String content = "<test>This is something to test with</test>";
        UriInfo uri = Mockito.mock(UriInfo.class);
        Mockito.when(uri.getRequestUri()).thenReturn(URI.create("http://localhost:8080/v1/test?with=param"));
        String result = RequestMessageHelpers.generateSignatureContent(uri, content);
        Assert.assertEquals("/v1/test?with=param&<test>Thisissomethingtotestwith</test>", result);
    }

    @Test
    public void generateSignatureContentFromXmlWithoutUrlParams() {
        String content = "<test>This is something to test with</test>";
        UriInfo uri = Mockito.mock(UriInfo.class);
        Mockito.when(uri.getRequestUri()).thenReturn(URI.create("http://localhost:8080/v1/test"));
        String result = RequestMessageHelpers.generateSignatureContent(uri, content);
        Assert.assertEquals("/v1/test?<test>Thisissomethingtotestwith</test>", result);
    }
}
