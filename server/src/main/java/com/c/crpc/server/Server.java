package com.c.crpc.server;

import com.c.crpc.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class Server {

    private final ExecutorService threadPool;
    private ServiceManager manager;
    private Serializer serializer;
    private int port;


    private Server() {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(20);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        threadPool = new ThreadPoolExecutor(
                corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                blockingQueue,
                threadFactory
        );
    }

    public Server(ServiceManager manager, Serializer serializer, int port) {
        this();
        this.manager = manager;
        this.serializer = serializer;
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("server starting....");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected : " + socket.getInetAddress());
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                threadPool.execute(new ServiceInvoker(manager, is, os, serializer));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
