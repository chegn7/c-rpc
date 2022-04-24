package com.c.crpc.client;

import com.c.crpc.common.utils.ReflectionUtil;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.transport.TransportClient;

import java.lang.reflect.Proxy;

public class ClientProxy {
    private ClientConfig config;
    private String host;
    private int port;
    private Serializer serializer;

    private TransportClient transportClient;

    public ClientProxy() {
        this(new ClientConfig());
    }

    public ClientProxy(ClientConfig config) {
        this.config = config;

        host = config.getHost();
        port = config.getPort();

        transportClient = ReflectionUtil.newInstance(config.getTransportClient());
        transportClient.init(host, port);

        serializer = ReflectionUtil.newInstance(config.getSerializer());

    }

    public <T> T getProxyInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},new RemoteInvoker(transportClient, serializer));
    }

}
