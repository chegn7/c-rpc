package com.c.crpc.serialization;
@Deprecated
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
