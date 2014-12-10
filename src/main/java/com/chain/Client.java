package com.chain;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
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
        this.setAuthorization();
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

    public Address getAddress(String address) throws Exception {
        Response res = this.get("/addresses/" + address);
        return GSON.fromJson(res.body().charStream(), Address.class);
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
            .post(RequestBody.create(JSON, body))
            .build();
        return this.httpClient.newCall(req).execute();
    }

    private Response get(String path) throws java.io.IOException {
        Request req = new Request.Builder()
            .url(this.url(path))
            .build();
        return this.httpClient.newCall(req).execute();
    }

    private URL url(String path) throws MalformedURLException {
        return new URL(this.chainURL.toString() + path);
    }
    private void setAuthorization() {
        final String user = this.chainURL.toString().split(":")[0];
        final String pass = this.chainURL.toString().split(":")[1];
        this.httpClient.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                return response.request()
                        .newBuilder()
                        .header("Authorization", Credentials.basic(user, pass))
                        .build();
            }
            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
    }

}
