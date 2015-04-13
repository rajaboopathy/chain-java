package com.chain;

import com.google.gson.annotations.SerializedName;

public class Wallet {
    @SerializedName("wallet_id")
    public String id;
    public String label;
    public String environment;
    @SerializedName("block_chain")
    public String blockChain;
    public Keychain[] keychains;
    @SerializedName("bucket_count")
    public Integer bucketCount;

    public static class Keychain {
        public String xpub;
        @SerializedName("xprv_encrypted")
        public String xprvEncrypted;
    }

    public static class Balance {
        @SerializedName("wallet_id")
        public String walletID;
        public BalancePart total;
        public BalancePart confirmed;
    }
}
