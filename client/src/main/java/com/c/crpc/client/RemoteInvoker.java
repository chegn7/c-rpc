package com.c.crpc.client;

import com.c.crpc.common.enumeration.ResponseCode;
import com.c.crpc.common.exception.ClientException;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.protocol.ServiceDescriptor;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author cheng
 * @date 2022-04-24 22:00:56
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private TransportClient transportClient;
    private Serializer serializer;

    public RemoteInvoker(TransportClient transportClient, Serializer serializer) {
        this.transportClient = transportClient;
        this.serializer = serializer;
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
        Response response = null;
        response = transportClient.send(request, serializer);
        if (response == null || response.getCode() != ResponseCode.SUCCESS.getCode()) {
            log.error("response failed.");
            throw new ClientException("response failed");
        }
        return response.getData();
    }
}
