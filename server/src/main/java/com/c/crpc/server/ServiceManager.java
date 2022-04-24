package com.c.crpc.server;

import com.c.crpc.common.utils.ReflectionUtil;
import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.ServiceDescriptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceManager {
    /**
     * 考虑到线程安全，services使用concurrentHashMap
     */
    Map<ServiceDescriptor, ServiceInstance> services;


    public ServiceManager() {
        services = new ConcurrentHashMap<>();
    }

    /**
     * 根据类和object实例注册服务
     * @param clazz
     * @param target
     * @param <T>
     */
    public synchronized  <T> void register(Class<T> clazz, T target) {
        for (Method method : ReflectionUtil.getPublicMethods(clazz)) {
            ServiceDescriptor descriptor = new ServiceDescriptor();
            descriptor.setMethodName(method.getName());
            descriptor.setInterfaceName(clazz.getName());
            descriptor.setParameterTypes(method.getParameterTypes());
            descriptor.setReturnType(method.getReturnType());
            ServiceInstance instance = new ServiceInstance();
            instance.setMethod(method);
            instance.setTarget(target);
            services.put(descriptor,instance);
        }
    }

    public synchronized ServiceInstance findService(Request request) {
        return services.get(request.getDescriptor());
    }
}
