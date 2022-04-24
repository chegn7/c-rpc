package com.c.crpc.alpha2;

import com.c.crpc.client.Client;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.protocol.ServiceDescriptor;
import com.c.crpc.serialization.kryo.KryoSerializer;

/**
 * @author cheng
 * @date 2022-04-24 16:59:03
 */
public class TestClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 3210;
        Client client = new Client(host,port);
        client.setSerializer(new KryoSerializer());
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setInterfaceName("com.c.crpc.services.service.Hello");
        descriptor.setMethodName("a");
        descriptor.setReturnType(String.class);
        descriptor.setParameterTypes(new Class[]{String.class});
        Request request = new Request();
        request.setDescriptor(descriptor);
        request.setArgs(new Object[]{"sendTest"});
        Response response = client.send(request, host, port);
        System.out.println(request);
        System.out.println(response);


    }
}
