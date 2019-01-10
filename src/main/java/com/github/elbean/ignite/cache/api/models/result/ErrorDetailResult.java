package com.github.elbean.ignite.cache.api.models.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * View model with more detail for Error responses.
 *
 * @author BStilson
 */
public class ErrorDetailResult extends ErrorResult {

    @JsonUnwrapped
    DateTime timestamp;

    @JsonUnwrapped
    String path;

    public ErrorDetailResult() {
        this.timestamp = DateTime.now(DateTimeZone.UTC);
    }

    public DateTime getTimestamp() {
        return this.timestamp;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("ErrorResult[error='%s'; errorMessage='%s'; timestamp='%s'; path='%' ]", error, errorMessage, timestamp, path);
    }
}
