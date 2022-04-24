package com.c.crpc.protocol;

import com.c.crpc.common.enumeration.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Response {
    // 响应状态码
    int code;
    // 响应消息
    String message;
    // 响应返回数据
    Object data;

    public Response() {
        code = ResponseCode.SUCCESS.getCode();
        message=ResponseCode.SUCCESS.getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (code != response.code) return false;
        if (!Objects.equals(message, response.message)) return false;
        return Objects.equals(data, response.data);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
