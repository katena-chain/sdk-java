/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.api;

import com.github.katenachain.entity.Tx;

/**
 * TxWrapper wraps a tx and its status.
 */
public class TxWrapper {
    private Tx tx;
    private TxStatus status;

    public Tx getTx() {
        return this.tx;
    }

    public TxStatus getStatus() {
        return this.status;
    }
}
