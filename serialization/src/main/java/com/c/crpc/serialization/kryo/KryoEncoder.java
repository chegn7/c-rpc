package com.c.crpc.serialization.kryo;

import com.c.crpc.serialization.Encoder;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import lombok.AllArgsConstructor;
@Deprecated
@AllArgsConstructor
public class KryoEncoder implements Encoder {
    @Override
    public byte[] encode(Object o) {
        Kryo kryo = KryoInstance.getKryo();
        Output output = new Output(1024, -1);
        kryo.writeObject(output,o);
        return output.getBuffer();
    }
}
