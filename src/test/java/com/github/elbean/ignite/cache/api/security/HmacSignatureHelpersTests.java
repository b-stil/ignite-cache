package com.github.elbean.ignite.cache.api.security;

import com.github.elbean.ignite.cache.api.security.HmacSignatureHelpers;
import org.junit.Assert;
import org.junit.Test;

public class HmacSignatureHelpersTests {

    @Test
    public void validateHmacSignature() {
        String content = "/v1/app-lock/something";
        String privateKey = "Q2Iv4774Sv4kzLugItdzyHyc512yFh2QbDdl/ARzeF5xD2UAGvD8In5gtuuzSMki";

        String result = HmacSignatureHelpers.getHmacSha1SignatureFromStringContent(content, privateKey);
        Assert.assertEquals("QTC/viNK4jfhPAXsvsJ66MUxfqs=", result);
    }
}
