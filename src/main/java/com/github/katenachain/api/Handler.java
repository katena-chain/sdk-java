/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.api;

import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.entity.Tx;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.TxDataState;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.RawResponse;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;
import com.github.katenachain.serializer.Serializer;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

import static java.lang.Math.ceil;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Handler provides helper methods to send and retrieve tx without directly interacting with the HTTP Client.
 */
public class Handler {

    private static final String CERTIFICATES_PATH = "certificates";
    private static final String SECRETS_PATH = "secrets";
    private static final String LAST_PATH = "last";
    private static final String TXS_PATH = "txs";
    private static final String COMPANIES_PATH = "companies";
    private static final String KEYS_PATH = "keys";

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
     * accepts a tx and sends it to the Api to return a tx status or throws an error.
     */
    public TxStatus sendTx(Tx tx) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.post(TXS_PATH, this.serializer.serialize(tx).getBytes());
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_ACCEPTED) {
            return (TxStatus) this.serializer.deserialize(jsonBody, TxStatus.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrapper or throws an error.
     */
    public TxWrapper retrieveLastCertificate(String id) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", CERTIFICATES_PATH, id, LAST_PATH));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrapper) this.serializer.deserialize(jsonBody, TxWrapper.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrappers or throws an error.
     */
    public TxWrappers retrieveCertificates(String id, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", CERTIFICATES_PATH, id), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrappers) this.serializer.deserialize(jsonBody, TxWrappers.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrappers or throws an error.
     */
    public TxWrappers retrieveSecrets(String id, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", SECRETS_PATH, id), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrappers) this.serializer.deserialize(jsonBody, TxWrappers.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a tx wrappers or throws an error.
     */
    public TxWrappers retrieveTxs(String txCategory, String id, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", TXS_PATH, txCategory, id), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxWrappers) this.serializer.deserialize(jsonBody, TxWrappers.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns the list of keyV1 for a company or throws an error.
     */
    public KeyV1[] retrieveCompanyKeys(String companyBcid, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", COMPANIES_PATH, companyBcid, KEYS_PATH), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (KeyV1[]) this.serializer.deserialize(jsonBody, KeyV1[].class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * signs a tx data and returns a new tx ready to be sent.
     */
    public Tx signTx(PrivateKey privateKey, String chainID, Date nonceTime, TxData txData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] txDataState = this.getTxDataState(chainID, nonceTime, txData);
        byte[] signature = privateKey.sign(txDataState);
        return new Tx(txData, nonceTime, signature, privateKey.getPublicKey());
    }

    /**
     * returns the sorted and marshaled json representation of a TxData ready to be signed.
     */
    public byte[] getTxDataState(String chainID, Date nonceTime, TxData txData) {
        return this.serializer.serialize(new TxDataState(chainID, nonceTime, txData)).getBytes();
    }
}
