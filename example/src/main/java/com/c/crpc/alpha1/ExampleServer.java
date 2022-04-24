package com.c.crpc.alpha1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleServer {
    public static void main(String[] args) {
        int port = 3210;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                System.out.println("read start");
                int length = dis.readInt();
                byte[] requestBytes = new byte[length];
                if (length > 0) {
                    dis.readFully(requestBytes);
                }
                System.out.println(requestBytes);
                System.out.println("read complete");
                System.out.println(requestBytes);
                System.out.println("write start");
                dos.writeInt(requestBytes.length);
                dos.write(requestBytes);
                System.out.println("write complete");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
