package com.c.crpc.serialization.kryo;

import com.c.crpc.common.exception.SerializeException;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.protocol.ServiceDescriptor;
import com.c.crpc.serialization.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class KryoSerializer implements Serializer {
    /**
     * Kryo 线程不安全，需要使用ThreadLocal存放不同线程的 Kryo
     */
    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        // 注册要序列化的类
        kryo.register(Request.class);
        kryo.register(Response.class);
        kryo.register(Object[].class);
        kryo.register(ServiceDescriptor.class);
        kryo.register(String[].class);

        // 设置
        kryo.setReferences(true);// 默认true，是否关闭注册行为
        kryo.setRegistrationRequired(false);// 默认false，是否关闭循环引用

        return kryo;
    });

    /**
     * 序列化对象，得到byte[]数组供网络传输
     *
     * @param obj
     * @return
     */
    @Override
    public byte[] serialize(Object obj) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Output output = new Output(bos);
        ) {
            kryo.writeObject(output, obj);
            KRYO_THREAD_LOCAL.remove();
            return output.toBytes();
        } catch (Exception e) {
            log.error("serialize error : " + e.getMessage(), e);
            throw new SerializeException("serialize error");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                Input input = new Input(bis);
        ) {
            T object = kryo.readObject(input, tClass);
            KRYO_THREAD_LOCAL.remove();
            return object;

        } catch (Exception e) {
            log.error("deserialize error : " + e.getMessage(), e);
            throw new SerializeException("deserialize error");
        }
    }
}
