/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.api;

import com.github.katenachain.entity.Tx;

/**
 * TxResult is returned by a GET request to retrieve a tx with useful information about its processing.
 */
public class TxResult {

    private String hash;
    private int height;
    private int index;
    private Tx tx;
    private TxStatus status;

    public Tx getTx() {
        return this.tx;
    }

    public TxStatus getStatus() {
        return this.status;
    }

    public String getHash() {
        return this.hash;
    }

    public int getHeight() {
        return this.height;
    }

    public int getIndex() {
        return this.index;
    }
}
