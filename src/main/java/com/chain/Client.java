package com.chain;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.*;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Client is the object that ties together everything needed to interact with api.chain.com
 * See https://chain.com/docs
 */
public class Client {

    /**
     * ChainException wraps errors returned by the API.
     * Each error contains a brief description in addition
     * to a unique error code. The error code can be used by
     * Chain Support to diagnose the exact cause of the error.
     */
    public static class ChainException extends Exception {
        public ChainException(String message) {
            super(message);
        }

        @SerializedName("message")
        public String chainMessage;
        @SerializedName("code")
        public String chainCode;

        public String getMessage()
        {
            return this.chainMessage + " " + this.chainCode;
        }
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String ChainVersion = "v3";
    public static Gson GSON = new Gson();
    private URL chainURL;
    private String blockChain;
    private OkHttpClient httpClient;

    /**
     * Create a new chain client.
     * @param chainURL E.g. https://KEY-ID:KEY-SECRET@api.chain.com/
     * @throws MalformedURLException
     */
    public Client(URL chainURL, String blockChain) throws MalformedURLException {
        this.chainURL = chainURL;
        this.blockChain = blockChain;
        this.httpClient = new OkHttpClient();
    }

    /**
     * Returns bitcoinj compatible network parameters based chainURL.
     * This is useful if you specified the network when instantiating a chain Client
     * and then need to interact with bitcoinj related functions which require NetworkParams.
     * @return org.bitcoinj.core.NetworkParameters
     */
    public NetworkParameters getNetworkParams() {
        if (this.blockChain.contains("testnet")) {
            return TestNet3Params.get();
        } else {
            return MainNetParams.get();
        }
    }

    public Address getAddress(String address) throws Exception {
        Response res = this.get("/" + this.blockChain + "/addresses/" + address);
        return GSON.fromJson(res.body().charStream(), Address[].class)[0];
    }

    public Address[] getAddress(String[] addresses) throws Exception {
        Response res = this.get("/" + this.blockChain + "/addresses/" + Joiner.on(',').join(addresses));
        return GSON.fromJson(res.body().charStream(), Address[].class);
    }

    public Transaction[] getAddressTransactions(String address) throws Exception {
        Response res = this.get("/" + this.blockChain + "/addresses/" + address + "/transactions");
        return GSON.fromJson(res.body().charStream(), Transaction[].class);
    }

    public Transaction.Output[] getAddressUnspents(String address) throws Exception {
        Response res = this.get("/" + this.blockChain + "/addresses/" + address + "/unspents");
        return GSON.fromJson(res.body().charStream(), Transaction.Output[].class);
    }

    public Transaction getTransaction(String transactionHash) throws Exception {
        Response res = this.get("/" + this.blockChain + "/transactions/" + transactionHash);
        return GSON.fromJson(res.body().charStream(), Transaction.class);
    }

    public Block getBlock(String blockHash) throws Exception {
        Response res = this.get("/" + this.blockChain + "/blocks/" + blockHash);
        return GSON.fromJson(res.body().charStream(), Block.class);
    }

    public Block getBlock(Integer blockHeight) throws Exception {
        Response res = this.get("/" + this.blockChain + "/blocks/" + blockHeight.toString());
        return GSON.fromJson(res.body().charStream(), Block.class);
    }

    public Block getLatestBlock() throws Exception {
        Response res = this.get("/" + this.blockChain + "/blocks/latest");
        return GSON.fromJson(res.body().charStream(), Block.class);
    }

    /**
     * Transact uses the API to build a template, signs the template locally,
     * and then submits the signed template back to the API for network propagation.
     * Private keys never leave the local process.
     * @param request E.g. {address: }
     * @param keys An array of private keys in WIF format.
     * @return
     * @throws Exception
     */
    public TransactionTemplate.Response transact(TransactionTemplate.Request request, String[] keys) throws Exception {
        TransactionTemplate template = this.buildTransaction(request);
        template.sign(this.getNetworkParams(), keys);
        return this.sendTransaction(template);
    }

    public TransactionTemplate buildTransaction(TransactionTemplate.Request request) throws Exception {
        Response res = this.post("/transactions/build", GSON.toJson(request));
        return GSON.fromJson(res.body().charStream(), TransactionTemplate.class);
    }

    public TransactionTemplate.Response sendTransaction(TransactionTemplate template) throws Exception {
        Response res = this.post("/transactions/send", GSON.toJson(template));
        return GSON.fromJson(res.body().charStream(), TransactionTemplate.Response.class);
    }

    private Response post(String path, String body) throws Exception {
        Request req = new Request.Builder()
                .url(this.url(path))
                .header("Authorization", this.credentials())
                .post(RequestBody.create(JSON, body))
                .build();
        Response resp = this.httpClient.newCall(req).execute();
        return this.checkError(resp);
    }

    private Response get(String path) throws Exception {
        Request req = new Request.Builder()
                .url(this.url(path))
                .header("Authorization", this.credentials())
                .build();
        Response resp = this.httpClient.newCall(req).execute();
        return this.checkError(resp);
    }

    private Response checkError(Response response) throws Exception {
        if ((response.code() / 100) != 2) {
            throw GSON.fromJson(response.body().charStream(), ChainException.class);
        }
        return response;
    }

    private URL url(String path) throws MalformedURLException {
        return new URL(this.chainURL.toString() + "/" + ChainVersion + "/" + path);
    }

    private String credentials() {
        String userInfo = this.chainURL.getUserInfo();
        String user = "";
        String pass = "";
        if (userInfo != null) {
            String[] parts = userInfo.split(":");
            if (parts.length >= 1) {
                user = parts[0];
            }
            if (parts.length >= 2) {
                pass = parts[1];
            }
        }
        return Credentials.basic(user, pass);
    }

}
