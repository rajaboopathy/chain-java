package com.chain;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Date;

public class Transaction {
    public String hash;
    @SerializedName("block_hash")
    public String blockHash;
    @SerializedName("block_height")
    public BigInteger blockHeight;
    @SerializedName("block_time")
    public Date blockTime;
    public BigInteger confirmations;
    public Input[] inputs;
    public Output[] outputs;
    public BigInteger fees;
    public BigInteger amount;

    public String getHash() {
        return hash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BigInteger getBlockHeight() {
        return blockHeight;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public BigInteger getConfirmations() {
        return confirmations;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public Output[] getOutputs() {
        return outputs;
    }

    public BigInteger getFees() {
        return fees;
    }

    public BigInteger getAmount() {
        return amount;
    }

    static public class Input {
        @SerializedName("transaction_hash")
        public String transactionHash;
        @SerializedName("output_hash")
        public String outputHash;
        @SerializedName("output_index")
        public Integer outputIndex;
        public BigInteger value;
        public String[] addresses;
        @SerializedName("script_signature")
        public String scriptSignature;
    }

    static public class Output {
        @SerializedName("transaction_hash")
        public String transactionHash;
        @SerializedName("output_index")
        public Integer outputIndex;
        public BigInteger value;
        public String[] addresses;
        public String script;
        @SerializedName("script_hex")
        public String scriptHex;
        @SerializedName("script_type")
        public String scriptType;
        @SerializedName("required_signatures")
        public Integer requiredSignatures;
        public Boolean spent;
    }
}
