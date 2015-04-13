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

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public Date getCreated() {
        return created;
    }

    public Date getExpires() {
        return expires;
    }

    public Components getAddressComponents() {
        return addressComponents;
    }

    public String getBlockChain() {
        return blockChain;
    }

    public static class Components {
        public Integer sigsRequired;
        public Signer[] signers;

        public Integer getSigsRequired() {
            return sigsRequired;
        }

        public Signer[] getSigners() {
            return signers;
        }
    }

    public static class Signer {
        public String entity;
        public String xpub;
        @SerializedName("derivation_path")
        public Integer[] derivationPath;
        public String pubkey;

        public String getEntity() {
            return entity;
        }

        public String getXpub() {
            return xpub;
        }

        public Integer[] getDerivationPath() {
            return derivationPath;
        }

        public String getPubkey() {
            return pubkey;
        }
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

        public BigInteger getAmount() {
            return amount;
        }

        public String getMemo() {
            return memo;
        }

        public Date getExpires() {
            return expires;
        }
    }
}
