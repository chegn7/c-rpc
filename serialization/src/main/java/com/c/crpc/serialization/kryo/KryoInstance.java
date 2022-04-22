package com.c.crpc.serialization.kryo;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.protocol.ServiceDescriptor;
import com.esotericsoftware.kryo.Kryo;

/**
 * 提供Kryo实例，只需要一个实例工具，考虑单例模式
 * 需要注册序列化、反序列化的对象
 */
@Deprecated
public class KryoInstance {
    private volatile static Kryo kryo;

    private KryoInstance() {
    }

    public static Kryo getKryo() {
        if (kryo == null) {
            synchronized (KryoInstance.class) {
                if (kryo == null) {
                    kryo = new Kryo();
                    kryo.register(Request.class);
                    kryo.register(Response.class);
                    kryo.register(Object[].class);
                    kryo.register(ServiceDescriptor.class);
                    kryo.register(String[].class);
                }
            }
        }
        return kryo;
    }
}
