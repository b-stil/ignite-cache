/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.elbean.ignite.cache.api.models;

import javax.ws.rs.WebApplicationException;

/**
 *
 * @author BStilson
 */
public enum DateUnit {
    SECONDS, MINUTES, HOURS;
    
    public DateUnit fromString(String param){
        String uppered = param.toUpperCase();
        try{
            return valueOf(uppered);
        }
        catch(Exception ex){
            throw new WebApplicationException(400);
        }
    }
}
