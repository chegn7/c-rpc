package com.c.crpc.alpha2;

import com.c.crpc.serialization.Serializer;
import com.c.crpc.serialization.kryo.KryoSerializer;
import com.c.crpc.server.Server;
import com.c.crpc.server.ServiceManager;
import com.c.crpc.services.implement.HelloImpl;
import com.c.crpc.services.service.Hello;

/**
 * @author cheng
 * @date 2022-04-24 16:01:30
 */
public class TestServer {
    public static void main(String[] args) {

        ServiceManager manager = new ServiceManager();
        Serializer serializer = new KryoSerializer();
        int port = 3210;
        HelloImpl hello = new HelloImpl();
        manager.register(Hello.class, hello);
        Server server = new Server(manager, serializer, port);
        server.start();
    }
}
