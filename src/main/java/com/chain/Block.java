package com.chain;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class Block {
    public String hash;
    @SerializedName("previous_block_hash") public String previousBlockHash;
    public Integer height;
    public Integer confirmations;
    @SerializedName("merkle_root") public String merkleRoot;
    public Date time;
    public Integer nonce;
    public BigDecimal difficulty;
    @SerializedName("transaction_hashes") public String[] transactionHashes;

    public String getHash() {
        return hash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public Date getTime() {
        return time;
    }

    public Integer getNonce() {
        return nonce;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public String[] getTransactionHashes() {
        return transactionHashes;
    }
}
