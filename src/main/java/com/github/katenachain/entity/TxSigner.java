/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity;

import com.github.katenachain.crypto.ED25519.PrivateKey;

/**
 * TxSigner contains all information about a Tx signer.
 */
public class TxSigner {

    private String fqId;
    private PrivateKey privateKey;

    /**
     * TxSigner constructor.
     *
     * @param fqId
     * @param privateKey
     */
    public TxSigner(String fqId, PrivateKey privateKey) {
        this.fqId = fqId;
        this.privateKey = privateKey;
    }

    public String getFqId() {
        return this.fqId;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
