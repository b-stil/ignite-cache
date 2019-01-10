package com.github.elbean.ignite.cache.api.models.validation.annotations;

import com.github.elbean.ignite.cache.api.models.DateUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author BStilson
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = MinDependsOnDateUnits.class)
public @interface MinDependsOnDateUnit {

    /**
     * @return Min value to compare against
     */
    int minValue() default 1;

    /**
     * @return DateUnit to apply the minValue to
     */
    DateUnit unit();

}
