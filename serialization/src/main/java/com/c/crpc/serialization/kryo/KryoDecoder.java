package com.c.crpc.serialization.kryo;

import com.c.crpc.serialization.Decoder;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
@Deprecated
public class KryoDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        Kryo kryo = KryoInstance.getKryo();
        Input input = new Input(bytes);
        return kryo.readObject(input, clazz);
    }
}
