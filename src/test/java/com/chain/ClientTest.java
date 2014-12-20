package com.chain;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import java.math.BigInteger;
import static org.junit.Assert.*;

public class ClientTest {

    @org.junit.Test
    public void testTransact() throws Exception {
        String j = "{\"inputs\":[{\"signatures_required\":1,\"signatures\":[{\"address\":\"1k32d...\",\"public_key\":\"!---insert-public-key-here---!\",\"hash_to_sign\":\"0426434...\",\"signature\":\"!---insert-signature-here---!\"}]}],\"miner_fee\":10000,\"unsigned_hex\":\"0100000001ec...\"}";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));
        server.play();

        TransactionTemplate.Request req = new TransactionTemplate.Request();
        req.addAddressInput("mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr");
        req.addOutput("mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr", 10000);

        Client c = new Client(server.getUrl("/"));
        TransactionTemplate template = c.buildTransaction(req);
        assertEquals(10000, template.getMinerFee());
    }

    @org.junit.Test
    public void testGetAddress() throws Exception {
        String j = "[{\"address\":\"17x23dNjXJLzGMev6R63uyRhMWP1VHawKc\",\"total\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0},\"confirmed\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0}}]";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));
        server.play();

        Client c = new Client(server.getUrl("/"));
        Address[] a = c.getAddress("17x23dNjXJLzGMev6R63uyRhMWP1VHawKc");
        assertEquals(new BigInteger("5000000000"), a[0].getTotal().getBalance());
    }

}