package com.c.crpc.transport;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.serialization.Serializer;

public interface TransportClient {
    void init(String host, int port);
    Response send(Request request, Serializer serializer);

    void close();
}
