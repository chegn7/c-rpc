package com.c.crpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(0,"ok"),
    FAIL(1, "response failed"),
    METHOD_NOT_FOUND(1, "find method failed"),
    INTERFACE_NOT_FOUND(1, "find interface failed");
    int code;
    String message;
}
