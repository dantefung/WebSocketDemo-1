package cn.martin6699.javaxwebsocketclient.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);


    public final static String toJson(Object obj) throws JsonProcessingException {


        return objectMapper.writeValueAsString(obj);
    }


    public final static <T> T fromJson(String json, Class<T> type) throws IOException {
        return objectMapper.readValue(json, type);
    }

    public final static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }

    public final static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
