package com.c.crpc.serialization.kryo;

import com.c.crpc.protocol.Request;
import com.c.crpc.protocol.Response;
import com.c.crpc.protocol.ServiceDescriptor;
import com.c.crpc.serialization.JSON.JSONSerializer;
import com.c.crpc.serialization.Serializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SerializeTest {
    Response response;
    Request request;
    Serializer serializer;

    @Before
    public void init() {
        response = new Response(0, "ok", "test data");
        request = new Request(new ServiceDescriptor(), new Object[3]);
//        serializer = new KryoSerializer();
        serializer = new JSONSerializer();

    }

    @Test
    public void deserialize() {
        byte[] req = serializer.serialize(request);
        byte[] resp = serializer.serialize(response);

        Request decodeRequest = serializer.deserialize(req, Request.class);
        Assert.assertNotNull(decodeRequest);
        Assert.assertEquals(request,decodeRequest);

        Response decodeResponse = serializer.deserialize(resp,Response.class);
        Assert.assertNotNull(decodeResponse);
        Assert.assertEquals(response,decodeResponse);
    }

    @Test
    public void serialize() {
        byte[] req = serializer.serialize(request);
        byte[] resp = serializer.serialize(response);
    }
}