package com.chain;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

public class Client {
    public static Gson GSON = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String apiCredentials;
    private String blockChain;
    private OkHttpClient httpClient;

    public Client(String apiKeyId, String apiKeySecret, String blockChain) {
        this.apiCredentials = Credentials.basic(apiKeyId, apiKeySecret);
        this.blockChain = blockChain;
        this.httpClient = new OkHttpClient();
    }

    public NetworkParameters getNetworkParams() {
        switch (this.blockChain) {
            case "bitcoin":
                return MainNetParams.get();
            case "testnet3":
                return TestNet3Params.get();
            default:
                return MainNetParams.get();
        }
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
            .url("https://api.chain.com/v2/" + this.blockChain + path)
            .header("Authorization", this.apiCredentials)
            .post(RequestBody.create(JSON, body))
            .build();
        return this.httpClient.newCall(req).execute();
    }

}
