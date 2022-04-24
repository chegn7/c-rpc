package com.c.crpc.server;

import com.c.crpc.common.utils.ReflectionUtil;
import com.c.crpc.serialization.Serializer;
import com.c.crpc.transport.RequestHandler;
import com.c.crpc.transport.TransportServer;

/**
 * @author cheng
 * @date 2022-04-24 22:11:54
 */
public class ServerProxy {
    private ServerConfig config;
    private Serializer serializer;
    private TransportServer transportServer;
    private ServiceManager serviceManager;

    private RequestHandler handler;

    public ServerProxy(ServerConfig config) {
        this.config = config;

        serializer = ReflectionUtil.newInstance(config.getSerializer());

        serviceManager = new ServiceManager();

        transportServer = ReflectionUtil.newInstance(config.getTransportServer());
        transportServer.init(config.getPort(), new RequestHandlerImpl(serviceManager, serializer));
    }

    public <T> void register(Class<T> clazz, T bean) {
        serviceManager.register(clazz, bean);
    }

    public void start() {
        transportServer.start();
    }

}
