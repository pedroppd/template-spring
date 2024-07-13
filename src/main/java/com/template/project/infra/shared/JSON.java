package com.template.project.infra.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {

    public static <T> T parse(String stringValue, Class<T> clazz) throws JsonProcessingException {
        try {
            return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(stringValue, clazz);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static <T> T parse(Object objectValue, Class<T> clazz) {
        try {
            return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(objectValue, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
