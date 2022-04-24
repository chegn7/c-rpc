package com.c.crpc.server;

import com.c.crpc.common.utils.ReflectionUtil;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.serialization.Serializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@AllArgsConstructor
@Data
public class ServiceInvoker implements Runnable {
    private ServiceManager manager;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Serializer serializer;

    public Object invoke(ServiceInstance instance, Request request) {
        return ReflectionUtil.invoke(instance.getTarget(), instance.getMethod(), request.getArgs());
    }

    @Override
    public void run() {
        Response response = new Response();
        // read length
        DataInputStream dis = new DataInputStream(inputStream);
        DataOutputStream dos = new DataOutputStream(outputStream);
        try {
            log.info("read start.");
            int length = dis.readInt();
            byte[] requestBytes = new byte[length];
            if (length > 0) {
                dis.readFully(requestBytes);
                Request request = serializer.deserialize(requestBytes, Request.class);
                log.info("read complete, request : " + request.toString());
                if (request != null) {
                    ServiceInstance instance = manager.findService(request);
                    Object invoke = invoke(instance, request);
                    response.setCode(0);
                    response.setMessage("ok");
                    response.setData(invoke);
                }
            } else {
                response.setCode(1);
                response.setMessage("request can not be null!");
            }
            byte[] responseBytes = serializer.serialize(response);
            log.info("write start.");
            dos.writeInt(responseBytes.length);
            dos.write(responseBytes);
            log.info("write complete, response : " + response);
        } catch (IOException e) {
            log.error("执行远程方法出错");
        }
    }
}
