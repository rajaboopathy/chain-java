package com.chain;

import com.google.gson.annotations.SerializedName;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;

import java.util.ArrayList;

public class TransactionTemplate {
    @SerializedName("miner_fee")
    public Integer minerFee;
    @SerializedName("unsigned_hex")
    public String unsignedHex;
    @SerializedName("inputs")
    public Input[] inputs;

    public int getMinerFee() {
        return minerFee;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public void sign(NetworkParameters parameters, String[] keys) throws AddressFormatException {
        new Signer(parameters, this, keys).sign();
    }

    static public class Response {
        @SerializedName("transaction_hash")
        public String transactionHash;

        public String getTransactionHash() {
            return transactionHash;
        }
    }

    static public class Request {
        public ArrayList<Input> inputs;
        public ArrayList<Output> outputs;

        public Request() {
            this.inputs = new ArrayList<Input>();
            this.outputs = new ArrayList<Output>();
        }

        public void addAddressInput(String address) {
            Input input = new Input();
            input.setAddress(address);
            this.inputs.add(input);
        }

        public void addPrivKeyInput(String privKey) {
            Input input = new Input();
            input.setPrivateKey(privKey);
            this.inputs.add(input);
        }

        public void addOutput(String address, Integer amount) {
            Output output = new Output();
            output.setAddress(address);
            output.setAmount(amount);
            this.outputs.add(output);
        }

        static public class Input {
            public String address;
            @SerializedName("private_key")
            public String privateKey;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPrivateKey() {
                return privateKey;
            }

            public void setPrivateKey(String privateKey) {
                this.privateKey = privateKey;
            }
        }

        static public class Output {
            public String address;
            public Integer amount;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = amount;
            }
        }
    }

    public class Input {
        @SerializedName("signatures_required")
        public Integer signaturesRequired;
        public Signature[] signatures;

        public int getSignaturesRequired() {
            return signaturesRequired;
        }

        public Signature[] getSignatures() {
            return signatures;
        }

        public class Signature {
            @SerializedName("public_key")
            public String publicKey;
            @SerializedName("hash_to_sign")
            public String hashToSign;
            public String signature;
            public String address;

            public String getAddress() {
                return address;
            }

            public String getPublicKey() {
                return publicKey;
            }

            public void setPublicKey(String publicKey) {
                this.publicKey = publicKey;
            }

            public String getHashToSign() {
                return hashToSign;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
