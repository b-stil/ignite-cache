package com.github.elbean.ignite.cache.api.models.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * View model to return validation errors to the client.
 * 
 * @author BStilson
 */
public class ValidationErrorResult extends ErrorDetailResult {

    @JsonUnwrapped
    Map<String, ArrayList<String>> validationErrors;

    public ValidationErrorResult() {
        this.validationErrors = new HashMap<String, ArrayList<String>>();
    }
       
    public void addValidationError(String field, String reason){
        if(validationErrors.get(field) == null){
            validationErrors.put(field, new ArrayList<String>());
        }
        validationErrors.get(field).add(reason);
    }
    
    public Map<String, ArrayList<String>> getValidationErrors(){
        return this.validationErrors;
    }
    
    @Override
    public String toString(){
        return String.format("ValidationError[ errorsCount='%s' ]", validationErrors.size());
    }
    
}
