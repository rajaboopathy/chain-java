package com.chain;

import com.google.gson.annotations.SerializedName;

public class TransactionTemplate {
    public class Input {
        @SerializedName("signatures_required") public Integer signaturesRequired;

        public class Signature {
            @SerializedName("public_key") public String publicKey;
            @SerializedName("hash_to_sign") public String hashToSign;
            public String signature;
            public String address;

            public String getAddress() {
                return address;
            }
            public String getPublicKey() {
                return publicKey;
            }
            public String getHashToSign() {
                return hashToSign;
            }
            public String getSignature() {
                return signature;
            }
            public void setPublicKey(String publicKey) {
                this.publicKey = publicKey;
            }
            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
        public Signature []signatures;

        public int getSignaturesRequired() {
            return signaturesRequired;
        }
        public Signature[] getSignatures() {
            return signatures;
        }
    }

    @SerializedName("miner_fee") public Integer minerFee;
    @SerializedName("unsigned_hex") public String unsignedHex;
    @SerializedName("inputs") public Input []inputs;

    public int getMinerFee() {
        return minerFee;
    }
    public Input[] getInputs() {
        return inputs;
    }
}
