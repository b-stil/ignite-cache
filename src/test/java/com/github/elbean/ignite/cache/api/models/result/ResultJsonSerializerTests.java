package com.github.elbean.ignite.cache.api.models.result;

import com.github.elbean.ignite.cache.api.models.result.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.elbean.ignite.cache.api.v1.models.applock.AppLockResponseDto;
import org.junit.Assert;
import org.junit.Test;

public class ResultJsonSerializerTests {

    @Test
    public void ResultJsonSerializerProducesAppLockResult() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Result<AppLockResponseDto> response = new Result<>();
        response.error = false;
        response.errorMessage = null;
        response.returnObject = new AppLockResponseDto();
        String result = mapper.writeValueAsString(response);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("appLock"));
    }

}
