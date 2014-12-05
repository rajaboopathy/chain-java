package com.chain;

import com.google.gson.Gson;
import org.bitcoinj.core.AddressFormatException;

import static org.junit.Assert.*;

public class SignerTest {

    @org.junit.Test
    public void testConstructor() throws AddressFormatException {
        String expectedSignature = "304402201bd4c0add3ed1df6f385019cddb3f073f9d66ec27191a4b5d479c82996b4ccc3022042e4ea27cc59136839856e97e7f354a07c24158168d8c9c4399739bcbfb810e0";
        String expectedPubKey = "0221d7f59fda41ae378f3e137d682b51e570982e86d6a01c2bf2fd48d7abb985e5";
        String []keys = {"cQeAZDF8QvY2EX3gZ8C1FyeGfHCD6i7Wa2ZEnoYyn14wvNJEsJWD"};
        String templateJson = "{\"inputs\":[{\"address\":\"mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr\",\"signatures_required\":1,\"signatures\":[{\"address\":\"mhALPqBgS91s7hVDEJbf9ri1KRnSxriJhr\",\"public_key\":\"<insert public key>\",\"hash_to_sign\":\"6dc692c5f8195225d975771bb565dc032e4cb43413a73bee47f41cb1870e9ea9\",\"signature\":\"<insert signature>\"}]}],\"miner_fee\":10000,\"unsigned_hex\":\"0100000001163a76f8836fc4e866eb615f28eecd8a1ad2d857f4b31b9e7c880c27c8cb9371000000001976a9141209f6d5acd78cd1c54a9f489b48a838179e327e88acffffffff0210270000000000001976a9141209f6d5acd78cd1c54a9f489b48a838179e327e88ac80380100000000001976a9141209f6d5acd78cd1c54a9f489b48a838179e327e88ac00000000\"}";

        Gson gson = new Gson();
        TransactionTemplate template = gson.fromJson(templateJson, TransactionTemplate.class);

        Signer signer = new Signer(template, keys);
        signer.sign();

        TransactionTemplate.Input.Signature signature = template.getInputs()[0].getSignatures()[0];
        assertEquals(expectedSignature, signature.getSignature());
        assertEquals(expectedPubKey, signature.getPublicKey());
    }
}