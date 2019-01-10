package com.github.elbean.ignite.cache.api.models.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ResultJsonSerializer.class)
public class Result<TReturn> extends ErrorResult {

    public TReturn returnObject;

    public TReturn getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(TReturn returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public String toString() {
        return String.format("Result[error='%s'; errorMessage='%s'; object=['%s']]", error, errorMessage, (returnObject != null) ? returnObject.toString() : "null");
    }
}
