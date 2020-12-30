package jmh;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperFactory {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T readValue(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(bytes, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot convert bytes into required type");
        }
    }
}