/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.api;

/**
 * TxResults is returned by a GET request to retrieve a list of TxResult with the total txs available.
 */
public class TxResults {

    private TxResult[] txs;
    private int total;

    public TxResult[] getTxs() {
        return this.txs;
    }

    public int getTotal() {
        return this.total;
    }
}
