package com.chain;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Date;

public class Receiver {
    @SerializedName("receiver_id")
    public String id;
    @SerializedName("receiver_address")
    public String address;
    @SerializedName("payment_details_message")
    public String paymentDetails;
    public Date created;
    public Date expires;
    @SerializedName("receiver_address_components")
    public Components addressComponents;
    @SerializedName("block_chain")
    public String blockChain;

    public void Verify() throws Exception {

    }

    public static class Components {
        public Integer sigsRequired;
        public Signer[] signers;
    }

    public static class Signer {
        public String entity;
        public String xpub;
        @SerializedName("derivation_path")
        public Integer[] derivationPath;
        public String pubkey;
    }

    public static class CreateRequest {
        public CreateRequest(BigInteger amt, Date expires, String memo) {
            this.amount = amt;
            this.expires = expires;
            this.memo = memo;
        }

        public BigInteger amount;
        public String memo;
        public Date expires;
    }
}
