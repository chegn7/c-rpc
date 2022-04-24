package com.c.crpc.common.exception;

/**
 * @author cheng
 * @date 2022-04-24 20:58:37
 */
public class ClientException extends RuntimeException{
    public ClientException(String message) {
        super(message);
    }
}
