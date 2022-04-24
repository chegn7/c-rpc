package com.c.crpc.transport;

public interface TransportServer {
    void init(int port, RequestHandler requestHandler);
    void start();
    void stop();
}
