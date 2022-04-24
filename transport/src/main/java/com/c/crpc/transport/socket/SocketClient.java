package com.c.crpc.transport.socket;

import com.c.crpc.common.exception.ClientException;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.transport.TransportClient;
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
public class SocketClient implements TransportClient {
    private int port;
    private String host;


    @Override
    public void init(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public Response send(Request request, Serializer serializer) {
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
            throw new ClientException("socket client error");
        }
    }

    @Override
    public void close() {
    }
}
