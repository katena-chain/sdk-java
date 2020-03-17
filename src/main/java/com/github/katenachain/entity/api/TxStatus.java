/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.api;

/**
 * TxStatus is a tx blockchain status.
 * 0: OK
 * 1: PENDING
 * Over 1: ERROR WITH CORRESPONDING CODE
 */
public class TxStatus {

    private int code;
    private String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
