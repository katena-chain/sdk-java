/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity;

import com.google.gson.annotations.SerializedName;
import com.github.katenachain.crypto.ED25519.PublicKey;

import java.util.Date;

/**
 * Tx wraps a tx data with its signature information and a nonce time to avoid replay attacks.
 */
public class Tx {

    private TxData data;
    @SerializedName("nonce_time")
    private Date nonceTime;
    private byte[] signature;
    private PublicKey signer;

    /**
     * Tx constructor.
     */
    public Tx(TxData data, Date nonceTime, byte[] signature, PublicKey signer) {
        this.data = data;
        this.nonceTime = nonceTime;
        this.signature = signature;
        this.signer = signer;
    }

    public Date getNonceTime() {
        return this.nonceTime;
    }

    public TxData getData() {
        return this.data;
    }

    public PublicKey getSigner() {
        return this.signer;
    }

    public byte[] getSignature() {
        return this.signature;
    }
}
