package com.c.crpc.serialization.JSON;

import com.alibaba.fastjson.JSON;
import com.c.crpc.serialization.Decoder;
@Deprecated
public class JSONDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes,clazz);
    }
}
