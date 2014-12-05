package com.chain;

import com.google.gson.Gson;
import static org.junit.Assert.*;

public class TransactionTemplateTest {
    @org.junit.Test
    public void testConstructor() {
        String j = "{\"inputs\"=>[{\"signatures_required\"=>1, \"signatures\"=>[{\"address\"=>\"1k32d...\", \"public_key\"=>\"!---insert-public-key-here---!\", \"hash_to_sign\"=>\"0426434...\", \"signature\"=>\"!---insert-signature-here---!\"}]}], \"miner_fee\"=>10000, \"unsigned_hex\"=>\"0100000001ec...\"}";
        Gson gson = new Gson();

        TransactionTemplate template = gson.fromJson(j, TransactionTemplate.class);
        assertEquals(10000, template.getMinerFee());

        TransactionTemplate.Input input = template.getInputs()[0];
        assertEquals(1, input.getSignaturesRequired());

        TransactionTemplate.Input.Signature sig = input.signatures[0];
        assertEquals("1k32d...", sig.getAddress());
    }
}