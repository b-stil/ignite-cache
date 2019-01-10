package com.github.elbean.ignite.cache.sharedKernel.helpers;

import com.github.elbean.ignite.cache.sharedKernel.helpers.StringHelpers;
import org.junit.Assert;
import org.junit.Test;

public class StringHelpersTests {

    @Test
    public void removeWhitespaceWhenContainsWhitespace() {
        String result = StringHelpers.removeWhiteSpace("This is something to test");
        Assert.assertEquals("Thisissomethingtotest", result);
    }

    @Test
    public void removeWhitespaceWhenInputIsNullShouldReturnEmptyString() {
        String result = StringHelpers.removeWhiteSpace(null);
        Assert.assertEquals("", result);
    }

    @Test
    public void removeWhitespaceWhenInputIsEmptyShouldReturnEmptyString() {
        String result = StringHelpers.removeWhiteSpace("");
        Assert.assertEquals("", "");
    }
}
