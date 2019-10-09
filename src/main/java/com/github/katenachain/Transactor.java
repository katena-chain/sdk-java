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
    private String companyChainId;
    private String chainId;
    private Serializer serializer;

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl, String chainId, String companyChainId, PrivateKey txSigner) {
        this.serializer = new Serializer();
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainId = chainId;
        this.txSigner = txSigner;
        this.companyChainId = companyChainId;
    }

    /**
     * Transactor constructor.
     */
    public Transactor(String apiUrl) {
        this.serializer = new Serializer();
        this.apiHandler = new Handler(apiUrl, this.serializer);
        this.chainId = "";
        this.txSigner = null;
        this.companyChainId = "";
    }

    /**
     * creates a CertificateRaw (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendCertificateRawV1(String uuid, byte[] value) throws IOException, ApiException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, ClientException {
        CertificateRawV1 certificate = new CertificateRawV1(Certify.formatBcid(companyChainId, uuid), value);
        Tx tx = getTx(certificate);
        return apiHandler.sendCertificate(tx);
    }

    /**
     * creates a CertificateEd25519 (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendCertificateEd25519V1(String uuid, PublicKey signer, byte[] signature) throws IOException, ApiException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, ClientException {
        CertificateEd25519V1 certificate = new CertificateEd25519V1(Certify.formatBcid(companyChainId, uuid), signature, signer);
        Tx tx = getTx(certificate);
        return apiHandler.sendCertificate(tx);
    }

    /**
     * fetches the API to find the corresponding tx and return a tx wrapper.
     */
    public TxWrapper retrieveCertificate(String companyChainId, String uuid) throws IOException, ApiException {
        return apiHandler.retrieveCertificate(Certify.formatBcid(companyChainId, uuid));
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers or an error.
     */
    public TxWrappers retrieveCertificatesHistory(String companyChainId, String uuid) throws IOException, ApiException {
        return apiHandler.retrieveCertificatesHistory(Certify.formatBcid(companyChainId, uuid));
    }

    /**
     * creates a SecretNaclBox (V1), wraps in a tx and sends it to the API.
     */
    public TxStatus sendSecretNaclBoxV1(String uuid, com.github.katenachain.crypto.Nacl.PublicKey sender, byte[] nonce, byte[] content) throws IOException, ApiException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, ClientException {
        SecretNaclBoxV1 secret = new SecretNaclBoxV1(content, Certify.formatBcid(companyChainId, uuid), nonce, sender);
        Tx tx = getTx(secret);
        return apiHandler.sendSecret(tx);
    }

    /**
     * fetches the API to find the corresponding txs and returns tx wrappers.
     */
    public TxWrappers retrieveSecrets(String companyChainId, String uuid) throws IOException, ApiException {
        return apiHandler.retrieveSecrets(Certify.formatBcid(companyChainId, uuid));
    }

    /**
     * signs a tx data and returns a new tx ready to be sent.
     */
    public Tx getTx(TxData txData) throws ClientException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (txSigner == null || companyChainId == null) {
            throw new ClientException("impossible to create txs without a private key or company chain id");
        }

        Date nonceTime = new Date();
        byte[] txDataState = getTxDataState(chainId, nonceTime, txData);

        return new Tx(txData, nonceTime, txSigner.sign(txDataState), txSigner.getPublicKey());
    }

    /**
     * returns the sorted and marshaled json representation of a TxData ready to be signed.
     */
    public byte[] getTxDataState(String chainId, Date nonceTime, TxData txData) {
        return serializer.serialize(new TxDataState(chainId, nonceTime, txData)).getBytes();
    }

}
