package com.c.crpc.transport.socket;

import com.c.crpc.common.exception.ServerException;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.transport.RequestHandler;
import com.c.crpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class SocketServer implements TransportServer {

    private ExecutorService threadPool;
    private Serializer serializer;
    private int port;
    private ServerSocket serverSocket;

    private RequestHandler handler;



    @Override
    public void init(int port, RequestHandler handler) {
        this.port = port;
        this.serializer = serializer;

        this.handler = handler;

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

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            log.info("server starting....");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected : " + socket.getInetAddress());
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                handler.init(is,os);
                threadPool.execute(handler);
            }
        } catch (IOException e) {
            throw new ServerException("socket server error");
        }
    }

    @Override
    public void stop() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log.info("stop socket server error : " + e.getMessage(), e);
                throw new ServerException("close socket server error");
            }
        }
        log.info("stop socket server success");
    }


}
