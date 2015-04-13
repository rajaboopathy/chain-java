package com.chain;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import java.math.BigInteger;
import java.net.URL;

import static org.junit.Assert.*;

public class ClientTest {

    @org.junit.Test
    public void testGetAddress() throws Exception {
        String j = "[{\"address\":\"17x23dNjXJLzGMev6R63uyRhMWP1VHawKc\",\"total\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0},\"confirmed\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0}}]";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));
        server.play();

        Client c = new Client(server.getUrl("/"), "bitcoin");
        Address address = c.getAddress("17x23dNjXJLzGMev6R63uyRhMWP1VHawKc");
        assertEquals(new BigInteger("5000000000"), address.getTotal().getBalance());
    }

    @org.junit.Test
    public void testGetAddresseses() throws Exception {
        String j = "[{\"address\":\"17x23dNjXJLzGMev6R63uyRhMWP1VHawKc\",\"total\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0},\"confirmed\":{\"balance\":5000000000,\"received\":5000000000,\"sent\":0}}]";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));
        server.play();

        Client c = new Client(server.getUrl("/"), "bitcoin");
        Address[] addresses = c.getAddress(new String[]{"17x23dNjXJLzGMev6R63uyRhMWP1VHawKc"});
        assertEquals(new BigInteger("5000000000"), addresses[0].getTotal().getBalance());
    }

    @org.junit.Test
    public void testGetTransaction() throws Exception {
        String j = "{\"hash\":\"277fcf7c52e3faad633c5b9a8b3833634614ffd49fb121a187b1f769dc6c61b3\",\"block_hash\":\"00000000000000000f51bf3aa8863663ef66e373c094474147ba54933a110051\",\"block_height\":335193,\"block_time\":\"2014-12-21T05:07:52Z\",\"chain_received_at\":\"2014-12-21T05:05:07.215Z\",\"confirmations\":1,\"lock_time\":0,\"inputs\":[{\"transaction_hash\":\"277fcf7c52e3faad633c5b9a8b3833634614ffd49fb121a187b1f769dc6c61b3\",\"output_hash\":\"3412da4cda16e7b2fbcfde7a0b0281d52e03e5ab91bb3f16bbcde07de6f3ce13\",\"output_index\":0,\"value\":556391555,\"addresses\":[\"1nuUPvRoKovzDVPgUnSCm3ThKYWA1rf46\"],\"script_signature\":\"304402200e0c7c39d52f53d9b73f173410a04801bb08011557e84fa364f1b7909cd2468302204a34f67a9c5be58ce6df1382a3fbecec413150db164419f0ee261af81d18da4101 030d7edeefd1f782c423a54175d814bffdb2eb80380f945ac11c5adff2ebad4e8e\",\"sequence\":4294967295}],\"outputs\":[{\"transaction_hash\":\"277fcf7c52e3faad633c5b9a8b3833634614ffd49fb121a187b1f769dc6c61b3\",\"output_index\":0,\"value\":537614750,\"addresses\":[\"1CJuDoA9RMSKxWK5nKNti4ykL7mzJub2Zw\"],\"script\":\"OP_DUP OP_HASH160 7c0c0bea96f5e8adb7d60c162f05a56702dbd6cb OP_EQUALVERIFY OP_CHECKSIG\",\"script_hex\":\"76a9147c0c0bea96f5e8adb7d60c162f05a56702dbd6cb88ac\",\"script_type\":\"pubkeyhash\",\"required_signatures\":1,\"spent\":false},{\"transaction_hash\":\"277fcf7c52e3faad633c5b9a8b3833634614ffd49fb121a187b1f769dc6c61b3\",\"output_index\":1,\"value\":18756805,\"addresses\":[\"14fjQUk58ATSh7RYvf361B6sTbQqexT7a9\"],\"script\":\"OP_DUP OP_HASH160 283b94e687a6f4483ab43f9c8c60aa26bc5b8735 OP_EQUALVERIFY OP_CHECKSIG\",\"script_hex\":\"76a914283b94e687a6f4483ab43f9c8c60aa26bc5b873588ac\",\"script_type\":\"pubkeyhash\",\"required_signatures\":1,\"spent\":false}],\"fees\":20000,\"amount\":556371555}";
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(j));
        server.play();

        Client c = new Client(server.getUrl("/"), "bitcoin");
        Transaction transaction = c.getTransaction("277fcf7c52e3faad633c5b9a8b3833634614ffd49fb121a187b1f769dc6c61b3");
        assertEquals("00000000000000000f51bf3aa8863663ef66e373c094474147ba54933a110051", transaction.getBlockHash());
    }
}