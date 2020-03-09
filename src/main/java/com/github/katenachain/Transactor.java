/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain;

import com.github.katenachain.api.Handler;
import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.Tx;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.TxDataState;
import com.github.katenachain.entity.account.Account;
import com.github.katenachain.entity.account.KeyCreateV1;
import com.github.katenachain.entity.account.KeyRevokeV1;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.Certify;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;
import com.github.katenachain.serializer.Serializer;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;

/**
 * Transactor provides helper methods to hide the complexity of Tx creation, signature and API dialog.
 */
public class Transactor {
    private PrivateKey txSigner;
    private Handler apiHandler;
    private String companyBcid;
    private String chainID;
    private Serializer serializer;

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl, String chainID, String companyBcid, PrivateKey txSigner) {
        this.serializer = new Serializer();
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainID = chainID;
        this.txSigner = txSigner;
        this.companyBcid = companyBcid;
    }

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl) {
        this.serializer = new Serializer();
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainID = "";
        this.txSigner = null;
        this.companyBcid = "";
    }

    /**
     * creates a CertificateRaw (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendCertificateRawV1(String uuid, byte[] value) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        CertificateRawV1 certificate = new CertificateRawV1(Common.formatTxid(this.companyBcid, uuid), value);
        return this.sendTx(certificate);
    }

    /**
     * creates a CertificateEd25519 (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendCertificateEd25519V1(String uuid, PublicKey signer, byte[] signature) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        CertificateEd25519V1 certificate = new CertificateEd25519V1(Common.formatTxid(this.companyBcid, uuid), signature, signer);
        return this.sendTx(certificate);
    }

    /**
     * creates a KeyCreate (V1) and sends it to the API.
     */
    public TxStatus sendKeyCreateV1(String uuid, PublicKey publicKey, String role) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        KeyCreateV1 keyCreate = new KeyCreateV1(Common.formatTxid(this.companyBcid, uuid), publicKey, role);
        return this.sendTx(keyCreate);
    }

    /**
     * creates a KeyRevoke (V1) and sends it to the API.
     */
    public TxStatus sendKeyRevokeV1(String uuid, PublicKey publicKey) throws ApiException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClientException {
        KeyRevokeV1 keyRevoke = new KeyRevokeV1(Common.formatTxid(this.companyBcid, uuid), publicKey);
        return this.sendTx(keyRevoke);
    }

    /**
     * creates a SecretNaclBox (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendSecretNaclBoxV1(String uuid, com.github.katenachain.crypto.Nacl.PublicKey sender, byte[] nonce, byte[] content) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, ApiException, ClientException {
        SecretNaclBoxV1 secret = new SecretNaclBoxV1(content, Common.formatTxid(this.companyBcid, uuid), nonce, sender);
        return this.sendTx(secret);
    }

    /**
     * signs and sends a tx to the Api.
     */
    public TxStatus sendTx(TxData txData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, ApiException, ClientException {
        if (this.txSigner == null || this.chainID.equals("")) {
            throw new ClientException("impossible to create txs without a private key or chain id");
        }

        Tx tx = this.apiHandler.signTx(this.txSigner, this.chainID, new Date(), txData);
        return this.apiHandler.sendTx(tx);
    }

    /**
     * fetches the API to find the corresponding tx and return a tx wrapper.
     */
    public TxWrapper retrieveLastCertificate(String companyBcid, String uuid) throws IOException, ApiException {
        return this.apiHandler.retrieveLastCertificate(Common.formatTxid(companyBcid, uuid));
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers or an error.
     */
    public TxWrappers retrieveCertificates(String companyBcid, String uuid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveCertificates(Common.formatTxid(companyBcid, uuid), page, txPerPage);
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers or an error.
     */
    public TxWrappers retrieveKeyCreateTxs(String companyBcid, String uuid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveTxs(Account.getCategoryKeyCreate(), Common.formatTxid(companyBcid, uuid), page, txPerPage);
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers or an error.
     */
    public TxWrappers retrieveKeyRevokeTxs(String companyBcid, String uuid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveTxs(Account.getCategoryKeyRevoke(), Common.formatTxid(companyBcid, uuid), page, txPerPage);
    }

    /**
     * fetches the API and returns the list of keyV1 for a company or an error.
     */
    public KeyV1[] retrieveCompanyKeys(String companyBcid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveCompanyKeys(companyBcid, page, txPerPage);
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers.
     */
    public TxWrappers retrieveSecrets(String companyBcid, String uuid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveSecrets(Common.formatTxid(companyBcid, uuid), page, txPerPage);
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers or an error.
     */
    public TxWrappers retrieveTxs(String txCategory, String companyBcid, String uuid, int page, int txPerPage) throws IOException, ApiException {
        return this.apiHandler.retrieveTxs(txCategory, Common.formatTxid(companyBcid, uuid), page, txPerPage);
    }
}
