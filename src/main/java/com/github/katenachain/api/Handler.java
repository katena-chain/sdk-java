/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.api;

import com.github.katenachain.entity.Tx;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.TxDataState;
import com.github.katenachain.entity.TxSigner;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.RawResponse;
import com.github.katenachain.entity.api.SendTxResult;
import com.github.katenachain.entity.api.TxResult;
import com.github.katenachain.entity.api.TxResults;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.serializer.Serializer;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;
import java.util.HashMap;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Handler provides helper methods to send and retrieve tx without directly interacting with the HTTP Client.
 */
public class Handler {

    private static final String LAST_PATH = "last";
    private static final String STATE_PATH = "state";
    private static final String TXS_PATH = "txs";
    private static final String CERTIFICATES_PATH = "certificates";
    private static final String SECRETS_PATH = "secrets";
    private static final String COMPANIES_PATH = "companies";
    private static final String KEYS_PATH = "keys";

    private Client apiClient;
    private Serializer serializer;

    /**
     * Handler constructor.
     *
     * @param apiUrl
     * @param serializer
     */
    public Handler(String apiUrl, Serializer serializer) {
        this.apiClient = new Client(apiUrl);
        this.serializer = serializer;
    }

    /**
     * fetches the API to return the last tx related to a certificate fqid.
     *
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResult retrieveLastCertificateTx(String fqId) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", CERTIFICATES_PATH, fqId, LAST_PATH));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResult) this.serializer.deserialize(jsonBody, TxResult.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return all txs related to a certificate fqid.
     *
     * @param fqId
     * @param page
     * @param txPerPage
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResults retrieveCertificateTxs(String fqId, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", CERTIFICATES_PATH, fqId), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResults) this.serializer.deserialize(jsonBody, TxResults.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return the last tx related to a secret fqid.
     *
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResult retrieveLastSecretTx(String fqId) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", SECRETS_PATH, fqId, LAST_PATH));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResult) this.serializer.deserialize(jsonBody, TxResult.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return all txs related to a secret fqid.
     *
     * @param txPerPage
     * @param page
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResults retrieveSecretTxs(String fqId, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", SECRETS_PATH, fqId), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResults) this.serializer.deserialize(jsonBody, TxResults.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return the last tx related to a key fqid.
     *
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResult retrieveLastKeyTx(String fqId) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", KEYS_PATH, fqId, LAST_PATH));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResult) this.serializer.deserialize(jsonBody, TxResult.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return all txs related to a key fqid.
     *
     * @param txPerPage
     * @param page
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResults retrieveKeyTxs(String fqId, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", KEYS_PATH, fqId), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResults) this.serializer.deserialize(jsonBody, TxResults.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API to return any tx by its hash.
     *
     * @param hash
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public TxResult retrieveTx(String hash) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s", TXS_PATH, hash));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (TxResult) this.serializer.deserialize(jsonBody, TxResult.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a key from the state.
     *
     * @param fqId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public KeyV1 retrieveKey(String fqId) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s", STATE_PATH, KEYS_PATH, fqId));
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (KeyV1) this.serializer.deserialize(jsonBody, KeyV1.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * fetches the API and returns a list of keys for a company from the state.
     *
     * @param txPerPage
     * @param page
     * @param companyBcId
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public KeyV1[] retrieveCompanyKeys(String companyBcId, int page, int txPerPage) throws IOException, ApiException {
        HashMap<String, String> queryParams = Common.getPaginationQueryParams(page, txPerPage);
        RawResponse apiResponse = this.apiClient.get(String.format("%s/%s/%s/%s", STATE_PATH, COMPANIES_PATH, companyBcId, KEYS_PATH), queryParams);
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_OK) {
            return (KeyV1[]) this.serializer.deserialize(jsonBody, KeyV1[].class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * accepts an encoded tx and sends it to the Api to return its status and its hash.
     *
     * @param tx
     * @return
     * @throws IOException
     * @throws ApiException
     */
    public SendTxResult sendTx(Tx tx) throws IOException, ApiException {
        RawResponse apiResponse = this.apiClient.post(TXS_PATH, this.serializer.serialize(tx).getBytes());
        String jsonBody = new String(apiResponse.getBody(), StandardCharsets.UTF_8);
        if (apiResponse.getStatusCode() == HTTP_ACCEPTED) {
            return (SendTxResult) this.serializer.deserialize(jsonBody, SendTxResult.class);
        } else {
            throw (ApiException) this.serializer.deserialize(jsonBody, ApiException.class);
        }
    }

    /**
     * signs a tx data and returns a new tx ready to be sent.
     *
     * @param chainId
     * @param nonceTime
     * @param txSigner
     * @param txData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public Tx signTx(TxSigner txSigner, String chainId, Instant nonceTime, TxData txData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] txDataState = this.getTxDataState(chainId, nonceTime, txData);
        byte[] signature = txSigner.getPrivateKey().sign(txDataState);
        return new Tx(txData, nonceTime, signature, txSigner.getFqId());
    }

    /**
     * returns the sorted and marshaled json representation of a TxData ready to be signed.
     *
     * @param txData
     * @param nonceTime
     * @param chainId
     * @return
     */
    public byte[] getTxDataState(String chainId, Instant nonceTime, TxData txData) {
        return this.serializer.serialize(new TxDataState(chainId, nonceTime, txData)).getBytes();
    }
}
