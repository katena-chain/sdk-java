/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.api;

/**
 * TxWrappers wraps a list of TxWrapper with the total txs available.
 */
public class TxWrappers {

    private TxWrapper[] txs;
    private int total;

    public TxWrapper[] getTxs() {
        return this.txs;
    }

    public int getTotal() {
        return this.total;
    }
}
