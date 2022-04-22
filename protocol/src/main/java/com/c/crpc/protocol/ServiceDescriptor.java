package com.c.crpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * 服务的描述器，确定一个服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDescriptor {
    // 服务的接口名
    private String interfaceName;
    // 服务的方法名
    private String methodName;
    // 服务的返回值类型
    private Class<?> returnType;
    // 服务的输入参数类型
    private Class<?>[] parameterTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceDescriptor that = (ServiceDescriptor) o;

        if (!Objects.equals(interfaceName, that.interfaceName)) return false;
        if (!Objects.equals(methodName, that.methodName)) return false;
        if (!Objects.equals(returnType, that.returnType)) return false;
        return Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = interfaceName != null ? interfaceName.hashCode() : 0;
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
