package com.chain;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static Gson GSON = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private URL chainURL;
    private String blockChain;
    private OkHttpClient httpClient;

    public Client(URL chainURL) throws MalformedURLException {
        this.chainURL = chainURL;
        this.blockChain = chainURL.toString().contains("testnet") ? "testnet3" : "bitcoin";
        this.httpClient = new OkHttpClient();
    }

    public Client(String apiKeyId, String apiKeySecret, String blockChain) {
        this.blockChain = blockChain;
    }

    public NetworkParameters getNetworkParams() {
        if (this.blockChain.contains("testnet")) {
            return TestNet3Params.get();
        } else {
            return MainNetParams.get();
        }
    }

    public Address[] getAddress(String address) throws Exception {
        Response res = this.get("/addresses/" + address);
        return GSON.fromJson(res.body().charStream(), Address[].class);
    }

    public Transaction[] getAddressTransactions(String address) throws Exception {
        Response res = this.get("/addresses" + address + "/transactions");
        return GSON.fromJson(res.body().charStream(), Transaction[].class);
    }

    public Transaction.Output[] getAddressUnspents(String address) throws Exception {
        Response res = this.get("/addresses" + address + "/unspents");
        return GSON.fromJson(res.body().charStream(), Transaction.Output[].class);
    }

    public Transaction getTransaction(String transactionHash) throws Exception {
        Response res = this.get("/transactions/" + transactionHash);
        return GSON.fromJson(res.body().charStream(), Transaction.class);
    }

    public Block getBlock(String blockHash) throws Exception {
        Response res = this.get("/blocks/" + blockHash);
        return GSON.fromJson(res.body().charStream(), Block.class);
    }

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

    private Response post(String path, String body) throws java.io.IOException {
        Request req = new Request.Builder()
                .url(this.url(path))
                .header("Authorization", this.credentials())
                .post(RequestBody.create(JSON, body))
                .build();
        return this.httpClient.newCall(req).execute();
    }

    private Response get(String path) throws java.io.IOException {
        Request req = new Request.Builder()
                .url(this.url(path))
                .header("Authorization", this.credentials())
                .build();
        return this.httpClient.newCall(req).execute();
    }

    private URL url(String path) throws MalformedURLException {
        return new URL(this.chainURL.toString() + path);
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
