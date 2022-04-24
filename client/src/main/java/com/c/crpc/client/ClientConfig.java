package com.c.crpc.client;

import com.c.crpc.serialization.Serializer;
import com.c.crpc.serialization.kryo.KryoSerializer;
import com.c.crpc.transport.TransportClient;
import com.c.crpc.transport.socket.SocketClient;
import lombok.Data;

/**
 * @author cheng
 * @date 2022-04-24 21:44:27
 */
@Data
public class ClientConfig {
    private Class<? extends TransportClient> transportClient = SocketClient.class;
    private Class<? extends Serializer> serializer = KryoSerializer.class;
    private int port = 3210;
    private String host = "127.0.0.1";
}
