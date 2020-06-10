/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain;

import com.github.katenachain.api.Handler;
import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.Tx;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.TxSigner;
import com.github.katenachain.entity.account.KeyCreateV1;
import com.github.katenachain.entity.account.KeyRevokeV1;
import com.github.katenachain.entity.account.KeyRotateV1;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.SendTxResult;
import com.github.katenachain.entity.api.TxResult;
import com.github.katenachain.entity.api.TxResults;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;
import com.github.katenachain.serializer.Serializer;
import com.github.katenachain.utils.Common;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;

/**
 * Transactor provides helper methods to hide the complexity of Tx creation, signature and API dialog.
 */
public class Transactor {

    private Handler apiHandler;
    private String chainId;
    private TxSigner txSigner;
    private Serializer serializer;

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl, String chainId, TxSigner txSigner) {
        this.serializer = new Serializer(new GsonBuilder());
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainId = chainId;
        this.txSigner = txSigner;
    }

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl) {
        this.serializer = new Serializer(new GsonBuilder());
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainId = "";
        this.txSigner = null;
    }

    /**
     * creates a CertificateRaw (V1) and sends it to the API.
     */
    public SendTxResult sendCertificateRawV1Tx(String id, byte[] value) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        CertificateRawV1 certificate = new CertificateRawV1(id, value);
        return this.sendTx(certificate);
    }

    /**
     * creates a CertificateEd25519 (V1) and sends it to the API.
     */
    public SendTxResult sendCertificateEd25519V1Tx(String id, PublicKey signer, byte[] signature) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        CertificateEd25519V1 certificate = new CertificateEd25519V1(id, signer, signature);
        return this.sendTx(certificate);
    }

    /**
     * creates a SecretNaclBox (V1) and sends it to the API.
     */
    public SendTxResult sendSecretNaclBoxV1Tx(String id, com.github.katenachain.crypto.Nacl.PublicKey sender, byte[] nonce, byte[] content) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, ApiException, ClientException {
        SecretNaclBoxV1 secret = new SecretNaclBoxV1(content, id, nonce, sender);
        return this.sendTx(secret);
    }

    /**
     * creates a KeyCreate (V1) and sends it to the API.
     */
    public SendTxResult sendKeyCreateV1Tx(String id, PublicKey publicKey, String role) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        KeyCreateV1 keyCreate = new KeyCreateV1(id, publicKey, role);
        return this.sendTx(keyCreate);
    }

    /**
     * creates a KeyRotate (V1) and sends it to the API.
     */
    public SendTxResult sendKeyRotateV1Tx(String id, PublicKey publicKey) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        KeyRotateV1 keyRotate = new KeyRotateV1(id, publicKey);
        return this.sendTx(keyRotate);
    }

    /**
     * creates a KeyRevoke (V1) and sends it to the API.
     */
    public SendTxResult sendKeyRevokeV1Tx(String id) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        KeyRevokeV1 keyRevoke = new KeyRevokeV1(id);
        return this.sendTx(keyRevoke);
    }

    /**
     * creates a tx from a tx data and the provided tx signer info and chain id, signs it, encodes it and sends it
     * to the api.
     */
    public SendTxResult sendTx(TxData txData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, ApiException, ClientException {
        if (this.txSigner == null || this.txSigner.getFqId().equals("") || this.txSigner.getPrivateKey() == null || this.chainId.equals("")) {
            throw new ClientException("impossible to create txs without a tx signer info or chain id");
        }

        Tx tx = this.apiHandler.signTx(this.txSigner, this.chainId, Instant.now(), txData);
        return this.apiHandler.sendTx(tx);
    }

    /**
     * fetches the API and returns the last tx related to a certificate fqid.
     */
    public TxResult retrieveLastCertificateTx(String companyBcId, String id) throws IOException, ApiException {
        return this.apiHandler.retrieveLastCertificateTx(Common.concatFqId(companyBcId, id));
    }

    /**
     * fetches the API and returns all txs related to a certificate fqid.
     */
    public TxResults retrieveCertificateTxs(String companyBcId, String id, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveCertificateTxs(Common.concatFqId(companyBcId, id), page, txPerPage);
    }

    /**
     * fetches the API and returns the last tx related to a secret fqid.
     */
    public TxResult retrieveLastSecretTx(String companyBcId, String id) throws IOException, ApiException {
        return this.apiHandler.retrieveLastSecretTx(Common.concatFqId(companyBcId, id));
    }

    /**
     * fetches the API and returns all txs related to a secret fqid.
     */
    public TxResults retrieveSecretTxs(String companyBcId, String id, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveSecretTxs(Common.concatFqId(companyBcId, id), page, txPerPage);
    }

    /**
     * fetches the API and returns the last tx related to a key fqid.
     */
    public TxResult retrieveLastKeyTx(String companyBcId, String id) throws IOException, ApiException {
        return this.apiHandler.retrieveLastKeyTx(Common.concatFqId(companyBcId, id));
    }

    /**
     * fetches the API and returns all txs related to a key fqid.
     */
    public TxResults retrieveKeyTxs(String companyBcId, String id, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveKeyTxs(Common.concatFqId(companyBcId, id), page, txPerPage);
    }

    /**
     * fetches the API and return any tx by its hash.
     */
    public TxResult retrieveTx(String hash) throws IOException, ApiException {
        return this.apiHandler.retrieveTx(hash);
    }

    /**
     * fetches the API and returns a key from the state.
     */
    public KeyV1 retrieveKey(String companyBcId, String id) throws IOException, ApiException {
        return this.apiHandler.retrieveKey(Common.concatFqId(companyBcId, id));
    }

    /**
     * fetches the API and returns a list of keys for a company from the state.
     */
    public KeyV1[] retrieveCompanyKeys(String companyBcId, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveCompanyKeys(companyBcId, page, txPerPage);
    }
}
