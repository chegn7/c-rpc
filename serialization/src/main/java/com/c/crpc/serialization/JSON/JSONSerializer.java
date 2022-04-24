package com.c.crpc.serialization.JSON;

import com.c.crpc.common.exception.SerializeException;
import com.c.crpc.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class JSONSerializer implements Serializer {
    // fastjson 也是线程不安全的
    // jackson 是线程安全的

    @Override
    public byte[] serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            mapper.writeValue(bos, obj);
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("serialize error : " + e.getMessage(), e);
            throw new SerializeException("serialize error");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, tClass);
        } catch (IOException e) {
            log.error("deserialize error : " + e.getMessage(), e);
            throw new SerializeException("deserialize error");
        }
    }
}
