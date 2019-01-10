package com.github.elbean.ignite.cache.api.models.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * View model for basic error responses.
 * 
 * @author BStilson
 */
public class ErrorResult {

    @JsonUnwrapped
    boolean error;

    @JsonUnwrapped
    String errorMessage;

    public ErrorResult() {
    }

    public ErrorResult(boolean error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString(){
        return String.format("ErrorResult: [ error='%s'; errorMessage='%s' ]", this.isError(), this.getErrorMessage());
    } 
}
