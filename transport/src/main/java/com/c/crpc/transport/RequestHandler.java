package com.c.crpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class RequestHandler implements Runnable{
    public abstract void init(InputStream receiveStream, OutputStream responseStream);
}
