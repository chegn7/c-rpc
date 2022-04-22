package com.c.crpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private ServiceDescriptor descriptor;
    private Object[] args;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (!Objects.equals(descriptor, request.descriptor)) return false;
        return Arrays.equals(args, request.args);
    }

    @Override
    public int hashCode() {
        int result = descriptor != null ? descriptor.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
