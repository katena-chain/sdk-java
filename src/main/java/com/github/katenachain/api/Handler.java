/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.api;

import com.github.katenachain.entity.Tx;
import com.github.katenachain.entity.api.RawResponse;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.serializer.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Handler provides helper methods to send and retrieve tx without directly interacting with the HTTP Client.
 */
public class Handler {

    private static final String ROUTE_CERTIFICATES = "certificates";
    private static final String ROUTE_SECRETS = "secrets";
    private static final String PATH_CERTIFY = "certify";
    private static final String PATH_HISTORY = "history";

    private Client apiClient;
    private Serializer serializer;

    /**
     * Handler constructor.
     */
    public Handler(String apiUrl, Serializer serializer) {
        this.apiClient = new Client(apiUrl);
        this.serializer = serializer;
    }

    /**
     * accepts a tx and sends it to the appropriate certificate API route.
     */
    public TxStatus sendCertificate(Tx tx) throws IOException, ApiException {
        return sendTx(String.format("%s/%s", ROUTE_CERTIFICATES, PATH_CERTIFY), tx);
    }

    /**
     * accepts a tx and sends it to the appropriate API route.
     */
    public TxStatus sendSecret(Tx tx) throws IOException, ApiException {
        return sendTx(String.format("%s/%s", ROUTE_SECRETS, PATH_CERTIFY), tx);
    }

    /**
     * fetches the API and returns a tx wrapper.
     */
    public TxWrapper retrieveCertificate(String id) throws IOException, ApiException {
        RawResponse apiResponse = apiClient.get(String.format("%s/%s", ROUTE_CERTIFICATES, id));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrapper) serializer.deserialize(jsonBody, TxWrapper.class);
        } else {
            throw (ApiException) serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrappers.
     */
    public TxWrappers retrieveCertificatesHistory(String id) throws IOException, ApiException {
        RawResponse apiResponse = apiClient.get(String.format("%s/%s/%s", ROUTE_CERTIFICATES, id, PATH_HISTORY));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrappers) serializer.deserialize(jsonBody, TxWrappers.class);
        } else {
            throw (ApiException) serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrapper list.
     */
    public TxWrappers retrieveSecrets(String id) throws IOException, ApiException {
        RawResponse apiResponse = apiClient.get(String.format("%s/%s", ROUTE_SECRETS, id));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrappers) serializer.deserialize(jsonBody, TxWrappers.class);
        } else {
            throw (ApiException) serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * tries to send a tx to the API and returns a tx status or throws an api error.
     */
    public TxStatus sendTx(String route, Tx tx) throws IOException, ApiException {
        RawResponse apiResponse = apiClient.post(route, serializer.serialize(tx).getBytes());
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_ACCEPTED) {
            return (TxStatus) serializer.deserialize(jsonBody, TxStatus.class);
        } else {
            throw (ApiException) serializer.deserialize(jsonBody, ApiException.class);
        }
    }
}
