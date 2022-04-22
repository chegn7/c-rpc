package com.c.crpc.serialization;

public interface Serializer {
    byte[] serialize(Object obj);
    <T> T deserialize(byte[] bytes, Class<T> tClass);
}
