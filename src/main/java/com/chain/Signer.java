package com.chain;

import org.bitcoinj.core.*;
import org.bitcoinj.params.TestNet3Params;

import java.util.HashMap;

public class Signer {
    private TransactionTemplate transactionTemplate;
    private HashMap<String, ECKey> keyMap;

    public Signer(TransactionTemplate t, String []keys) throws AddressFormatException {
        this.transactionTemplate = t;
        this.keyMap = new HashMap<String, ECKey>();

        for(String key : keys) {
            ECKey ecKey = new DumpedPrivateKey(TestNet3Params.get(), key).getKey();
            Address address = ecKey.toAddress(TestNet3Params.get());
            this.keyMap.put(address.toString(), ecKey);
        }
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void sign() {
        for (TransactionTemplate.Input input : this.transactionTemplate.getInputs()) {
            for(TransactionTemplate.Input.Signature sig : input.getSignatures()) {
                ECKey key = this.keyMap.get(sig.getAddress());
                if (key == null) {
                    continue;
                }
                Sha256Hash hashToSign = new Sha256Hash(sig.getHashToSign());
                ECKey.ECDSASignature signature = key.sign(hashToSign);

                sig.setSignature(Utils.HEX.encode(signature.encodeToDER()));
                sig.setPublicKey(Utils.HEX.encode(key.getPubKey()));
            }
        }
    }
}
