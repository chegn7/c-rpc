package com.c.crpc.services.implement;

import com.c.crpc.services.service.Hello;

public class HelloImpl implements Hello {
    @Override
    public String a(String s) {
        return s + s + s;
    }
}
