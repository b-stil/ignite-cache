package com.github.elbean.ignite.cache.api.v1.models.applock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elbean.ignite.cache.api.models.validation.annotations.Required;
import javax.validation.constraints.Min;

import javax.ws.rs.QueryParam;

/**
 * View Model to query for an existing app-lock.
 * 
 * @author BStilson
 */
public class AppLockQueryDto {

    @QueryParam("referenceId")
    @JsonProperty
    @Min(value = 1, message = "required with a minimum value of {value}")
    int referenceId;

    @QueryParam("owner")
    @JsonProperty
    @Required
    String owner;

    @QueryParam("component")
    @JsonProperty
    @Required
    String component;

    //region getters/setters
    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
    //endregion

    @Override
    public String toString() {
        return String.format("AppLockQueryDto: [referenceId='%s'; owner='%s'; component='%s']", getReferenceId(), getOwner(), getComponent());
    }

}
