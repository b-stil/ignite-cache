package com.github.elbean.ignite.cache.api.v1.models.applock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elbean.ignite.cache.api.models.DateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MaxDuration;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnit;
import com.github.elbean.ignite.cache.api.models.validation.annotations.ValueInOptions;
import org.joda.time.DateTime;
import javax.ws.rs.QueryParam;
import com.github.elbean.ignite.cache.api.models.validation.annotations.MinDependsOnDateUnits;
import org.joda.time.DateTimeZone;

/**
 * View Model for controller binding to generate or update an app-lock.
 *
 * @author BStilson
 */
@MinDependsOnDateUnits(dateUnitField = "unitOfDuration", minValidAgainstField = "duration", value = {
    @MinDependsOnDateUnit(minValue = 10, unit = DateUnit.SECONDS),
        @MinDependsOnDateUnit(minValue = 3, unit = DateUnit.MINUTES),
        @MinDependsOnDateUnit(minValue = 1, unit = DateUnit.HOURS)
})
@MaxDuration(value = 7200, dateUnitField = "unitOfDuration", durationField = "duration")
public class AppLockDto extends AppLockQueryDto {

    @JsonIgnore
    DateTime startDT;

    @QueryParam("duration")
    @JsonProperty
    int duration;

    @QueryParam("unitOfDuration")
    @JsonProperty
    @ValueInOptions(enumClass = DateUnit.class)
    String unitOfDuration;

    public AppLockDto() {
        this.startDT = DateTime.now(DateTimeZone.UTC);
    }
  
    public DateTime getStartDT() {
        return startDT;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @JsonIgnore
    public DateUnit getUnitOfDurationAsDateUnit() {
        return DateUnit.valueOf(unitOfDuration);
    }

    public String getUnitOfDuration() {
        return this.unitOfDuration;
    }

    public void setUnitOfDuration(String unitOfDuration) {
        this.unitOfDuration = unitOfDuration.toUpperCase();
    }

    public void setUnitOfDuration(DateUnit dateUnit) {
        this.unitOfDuration = dateUnit.name();
    }
    //endregion

    @Override
    public String toString() {
        return String.format("AppLockDto: [referenceId='%s'; owner='%s'; component='%s'; startDT='%s'; duration='%s'; unitOfDuration='%s']",
                getReferenceId(), getOwner(), getComponent(), getStartDT().toString("MM-dd-yyyy HH:mm:ss.SSS"), getDuration(), getUnitOfDuration().toString());
    }
}
