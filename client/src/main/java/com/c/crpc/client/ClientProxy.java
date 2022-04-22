package com.c.crpc.client;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.ServiceDescriptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;

    public ClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T getProxyInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        ServiceDescriptor descriptor = ServiceDescriptor.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .returnType(method.getReturnType())
                .parameterTypes(method.getParameterTypes())
                .build();
        request.setDescriptor(descriptor);
        request.setArgs(args);
        return new Client().send(request,host,port);
    }


}
