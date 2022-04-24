package com.c.crpc.server;

import com.c.crpc.serialization.Serializer;
import com.c.crpc.serialization.kryo.KryoSerializer;
import com.c.crpc.transport.TransportServer;
import com.c.crpc.transport.socket.SocketServer;
import lombok.Data;

/**
 * @author cheng
 * @date 2022-04-24 22:12:02
 */
@Data
public class ServerConfig {
    private Class<? extends Serializer> serializer = KryoSerializer.class;
    private Class<? extends TransportServer> transportServer = SocketServer.class;
    private int port = 3210;
}
