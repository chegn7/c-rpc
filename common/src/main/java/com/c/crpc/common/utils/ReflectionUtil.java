package com.c.crpc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * @date 2022年4月24日 21点00分
 */
@Slf4j
public class ReflectionUtil {

    /**
     * 根据传入的clazz调用空参构造器创建对应类型的对象实例
     * @param clazz 待创建对象的类
     * @return 创建好的对象实例
     * @param <T> 对象类型
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("failed to create instance : " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取类声明的公共方法
     * @param clazz
     * @return
     */
    public static Method[] getPublicMethods(Class clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methods = new ArrayList<>();
        for (Method method : declaredMethods) {
            if (Modifier.isPublic(method.getModifiers())) methods.add(method);
        }
        return methods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     * @param obj 如果方法为静态方法，传入null
     * @param method
     * @param args
     * @return
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        // 如果这个方法不是这个对象所声明的，抛出异常
        if (obj != null && !method.getDeclaringClass().isAssignableFrom(obj.getClass())) {
            log.error("method can not match object");
            throw new IllegalStateException("method can not match object");
        }
        try {
            Object invokeRes = method.invoke(obj, args);
            return invokeRes;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
