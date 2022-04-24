package com.c.crpc.common.exception;

/**
 * @author cheng
 * @date 2022-04-24 20:58:04
 */
public class ServerException extends RuntimeException{
    public ServerException(String message) {
        super(message);
    }
}
