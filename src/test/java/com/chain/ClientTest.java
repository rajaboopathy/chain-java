package com.chain;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.Test;

import com.chain.TransactionTemplate.Request;
import static org.junit.Assert.*;

public class ClientTest {

    @org.junit.Test
    public void testTransact() throws Exception {
        String j = "{\"inputs\"=>[{\"signatures_required\"=>1, \"signatures\"=>[{\"address\"=>\"1k32d...\", \"public_key\"=>\"!---insert-public-key-here---!\", \"hash_to_sign\"=>\"0426434...\", \"signature\"=>\"!---insert-signature-here---!\"}]}], \"miner_fee\"=>10000, \"unsigned_hex\"=>\"0100000001ec...\"}";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));

        TransactionTemplate.Request req = new TransactionTemplate.Request();
        req.addAddressInput("mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr");
        req.addOutput("mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr", 10000);

        Client c = new Client("GUEST-TOKEN", "", "testnet3");
        TransactionTemplate template = c.buildTransaction(req);
        assertEquals(10000, template.getMinerFee());
    }
}