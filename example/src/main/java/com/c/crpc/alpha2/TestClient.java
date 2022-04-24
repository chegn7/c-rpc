package com.c.crpc.alpha2;

import com.c.crpc.client.ClientConfig;
import com.c.crpc.client.ClientProxy;
import com.c.crpc.services.service.Hello;

/**
 * @author cheng
 * @date 2022-04-24 16:59:03
 */
public class TestClient {
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        ClientProxy clientProxy = new ClientProxy(config);
        Hello hello = clientProxy.getProxyInstance(Hello.class);
        String testString = hello.a("testString");
        System.out.println(testString);
    }
}
