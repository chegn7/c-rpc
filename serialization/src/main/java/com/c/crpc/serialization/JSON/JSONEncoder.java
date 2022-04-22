package com.c.crpc.serialization.JSON;

import com.alibaba.fastjson.JSON;
import com.c.crpc.serialization.Encoder;
@Deprecated
public class JSONEncoder implements Encoder {
    @Override
    public byte[] encode(Object o) {
        return JSON.toJSONBytes(o);
    }
}
