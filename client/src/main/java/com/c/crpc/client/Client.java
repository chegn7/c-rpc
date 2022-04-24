package com.c.crpc.client;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.serialization.Serializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
@Data
@NoArgsConstructor
public class Client {
    private int port;
    private String host;
    private Serializer serializer;
    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }
    public Response send(Request request, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            byte[] requestBytes = serializer.serialize(request);
            log.info("write start");
            dos.writeInt(requestBytes.length);
            dos.write(requestBytes);
            log.info("write complete");
            log.info("read start");
            int length = dis.readInt();
            log.info("response byte array length : " + length);
            byte[] responseBytes = new byte[length];
            if (length > 0) dis.readFully(responseBytes);
            else throw new IllegalStateException();
            log.info("read complete");
            return serializer.deserialize(responseBytes, Response.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
