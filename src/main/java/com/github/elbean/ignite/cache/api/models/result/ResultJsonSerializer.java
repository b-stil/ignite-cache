package com.github.elbean.ignite.cache.api.models.result;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.annotation.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultJsonSerializer extends JsonSerializer<Result> {

    private static Logger LOGGER = LoggerFactory.getLogger(ResultJsonSerializer.class);

    @SuppressWarnings("unchecked")
    @Override
    public void serialize(Result value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        LOGGER.trace("Serializing response object");
        String objectName = "data";
        if (value.getReturnObject() != null) {
            Class c = value.getReturnObject().getClass();
            //see if the class represented by obj has a defined ResultObjectName annotation to define what the object property name should be
            Annotation annotation = c.getAnnotation(ResultObjectName.class);
            if (annotation != null && annotation instanceof ResultObjectName) {
                ResultObjectName root = (ResultObjectName) annotation;
                objectName = root.value();
            }
        } else {
            LOGGER.trace("No response data object was found to serialize");
        }
        gen.writeStartObject();
        if (value.getReturnObject() != null) {
            gen.writeObjectField(objectName, value.getReturnObject());
        }
        gen.writeBooleanField("error", value.isError());
        if (value.getErrorMessage() != null && !value.getErrorMessage().isEmpty()) {
            gen.writeStringField("errorMessage", value.getErrorMessage());
        }
        gen.writeEndObject();
        LOGGER.trace("Result serialized");
    }
}
