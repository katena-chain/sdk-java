/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.api;

import com.github.katenachain.entity.api.RawResponse;
import com.github.katenachain.utils.Common;
import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Client is a okhttp3.OkHttpClient wrapper to dialog with a JSON API.
 */
public class Client {

    private final OkHttpClient okHttpClient;
    private String apiUrl;

    /**
     * Client constructor.
     * @param apiUrl
     */
    public Client(String apiUrl) {
        this.apiUrl = apiUrl;
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * wraps the doRequest method to do a GET HTTP request.
     * @param route
     * @return
     * @throws IOException
     */
    public RawResponse get(String route) throws IOException {
        return this.doRequest("GET", route, null, new HashMap<>());
    }

    /**
     * wraps the doRequest method to do a GET HTTP request.
     * @param route
     * @param queryValues
     * @return
     * @throws IOException
     */
    public RawResponse get(String route, HashMap<String, String> queryValues) throws IOException {
        return this.doRequest("GET", route, null, queryValues);
    }

    /**
     * wraps the doRequest method to do a POST HTTP request.
     * @param route
     * @param body
     * @return
     * @throws IOException
     */
    public RawResponse post(String route, byte[] body) throws IOException {
        return this.doRequest("POST", route, body, new HashMap<>());
    }

    /**
     * wraps the doRequest method to do a POST HTTP request.
     * @param body
     * @param route
     * @param queryValues
     * @return
     * @throws IOException
     */
    public RawResponse post(String route, byte[] body, HashMap<String, String> queryValues) throws IOException {
        return this.doRequest("POST", route, body, queryValues);
    }

    /**
     * uses the okhttp3.OkHttpClient to call a distant api and returns a response.
     * @param queryValues
     * @param route
     * @param body
     * @param method
     * @return
     * @throws IOException
     */
    private RawResponse doRequest(String method, String route, byte[] body, HashMap<String, String> queryValues) throws IOException {

        HttpUrl httpUrl = Common.getUri(this.apiUrl, new String[]{route}, queryValues);

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(httpUrl);

        if (body != null) {
            RequestBody requestBody = RequestBody.create(null, body);
            requestBuilder.method(method, requestBody);
        } else {
            requestBuilder.method(method, null);
        }

        OkHttpClient requestClient = this.okHttpClient.newBuilder().protocols(new ArrayList<Protocol>() {{
            add(Protocol.HTTP_1_1);
        }}).build();
        Response response = requestClient.newCall(requestBuilder.build()).execute();

        byte[] responseBody = response.body() != null ? response.body().bytes() : null;

        return new RawResponse(response.code(), responseBody);

    }

}
