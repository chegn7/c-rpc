package com.c.crpc.server;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class ServiceInstance {
    private Object target;
    private Method method;
}
