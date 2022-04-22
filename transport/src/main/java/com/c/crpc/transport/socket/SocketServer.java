package com.c.crpc.transport.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SocketServer {
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected : "
                        + socket.getInetAddress().getHostName()
                        + ":" + socket.getInetAddress().getHostAddress());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                String msg = (String) ois.readObject();
                log.info("server receive message : " + msg);
                oos.writeObject("this is a message from server");
                oos.flush();
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        int port = 3210;
        ExecutorService threadPool = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(20));
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                server.start(port);
            }
        });
    }
}
