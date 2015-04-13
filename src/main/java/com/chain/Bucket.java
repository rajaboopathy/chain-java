package com.chain;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Date;

public class Bucket {
    @SerializedName("bucket_id")
    public String id;
    @SerializedName("wallet_index")
    public String walletIndex;
    @SerializedName("block_chain")
    public String blockChain;

    public String getId() {
        return id;
    }

    public String getWalletIndex() {
        return walletIndex;
    }

    public String getBlockChain() {
        return blockChain;
    }

    public static class Balance {
        public String bucketID;
        public String walletID;
        public BalancePart total;
        public BalancePart confirmed;

        public String getBucketID() {
            return bucketID;
        }

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

    public static class ActivityItem {
        @SerializedName("transaction_id")
        public String transactionID;
        public String type;
        public BigInteger amount;
        @SerializedName("bucket_id")
        public String bucketID;
        @SerializedName("receiver_id")
        public String receiverID;
        public Date timestamp;
        public Integer confirmations;

        public String getTransactionID() {
            return transactionID;
        }

        public String getType() {
            return type;
        }

        public BigInteger getAmount() {
            return amount;
        }

        public String getBucketID() {
            return bucketID;
        }

        public String getReceiverID() {
            return receiverID;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public Integer getConfirmations() {
            return confirmations;
        }
    }
}
