/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

/**
 * Tx wraps a tx data with its signature information and a nonce time to avoid replay attacks.
 */
public class Tx {

    private TxData data;
    @SerializedName("nonce_time")
    private Instant nonceTime;
    private byte[] signature;
    @SerializedName("signer_fqid")
    private String signerFqId;

    /**
     * Tx constructor.
     *
     * @param signerFqId
     * @param signature
     * @param data
     * @param nonceTime
     */
    public Tx(TxData data, Instant nonceTime, byte[] signature, String signerFqId) {
        this.data = data;
        this.nonceTime = nonceTime;
        this.signature = signature;
        this.signerFqId = signerFqId;
    }

    public Instant getNonceTime() {
        return this.nonceTime;
    }

    public TxData getData() {
        return this.data;
    }

    public String getSignerFqId() {
        return this.signerFqId;
    }

    public byte[] getSignature() {
        return this.signature;
    }
}
