/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.api;

/**
 * SendTxResult is returned by a POST request to retrieve the tx status and its hash.
 */
public class SendTxResult {

    private String hash;
    private TxStatus status;

    public String getHash() {
        return this.hash;
    }

    public TxStatus getStatus() {
        return this.status;
    }
}
