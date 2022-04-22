package com.c.crpc.client;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.serialization.kryo.KryoSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class Client {
    Serializer serializer;
    public Client() {
        serializer  = new KryoSerializer();
    }
    public Response send(Request request, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            os.write(serializer.serialize(request));
            os.flush();
            byte[] bytes = is.readAllBytes();
            return serializer.deserialize(bytes, Response.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
