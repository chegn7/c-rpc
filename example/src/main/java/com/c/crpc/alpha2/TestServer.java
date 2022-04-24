package com.c.crpc.alpha2;

import com.c.crpc.server.ServerConfig;
import com.c.crpc.server.ServerProxy;
import com.c.crpc.services.implement.HelloImpl;
import com.c.crpc.services.service.Hello;

/**
 * @author cheng
 * @date 2022-04-24 16:01:30
 */
public class TestServer {
    public static void main(String[] args) {
        ServerConfig config = new ServerConfig();
        ServerProxy serverProxy = new ServerProxy(config);
        Hello hello = new HelloImpl();
        serverProxy.register(Hello.class,hello);
        serverProxy.start();
    }
}
