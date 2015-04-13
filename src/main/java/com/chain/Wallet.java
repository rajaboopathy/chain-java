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

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getBlockChain() {
        return blockChain;
    }

    public Keychain[] getKeychains() {
        return keychains;
    }

    public Integer getBucketCount() {
        return bucketCount;
    }

    public static class Keychain {
        public String xpub;
        @SerializedName("xprv_encrypted")
        public String xprvEncrypted;

        public String getXpub() {
            return xpub;
        }

        public String getXprvEncrypted() {
            return xprvEncrypted;
        }
    }

    public static class Balance {
        @SerializedName("wallet_id")
        public String walletID;
        public BalancePart total;
        public BalancePart confirmed;

        public String getWalletID() {
            return walletID;
        }

        public BalancePart getTotal() {
            return total;
        }

        public BalancePart getConfirmed() {
            return confirmed;
        }
    }
}
