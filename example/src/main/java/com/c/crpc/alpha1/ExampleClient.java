package com.c.crpc.alpha1;

import java.io.*;
import java.net.Socket;

public class ExampleClient {
    public static void main(String[] args) {
        int port = 3210;
        String host = "127.0.0.1";
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            byte[] requestBytes = new byte[]{1, 2, 3, 4, 5};
            System.out.println("write start");
            dos.writeInt(requestBytes.length);
            dos.write(requestBytes);
            System.out.println("write complete");
            System.out.println("read start");
            int length = dis.readInt();
            byte[] respBytes = new byte[length];
            if (length > 0) {
                dis.readFully(respBytes);
            }
            System.out.println(respBytes);
            System.out.println("read complete");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
