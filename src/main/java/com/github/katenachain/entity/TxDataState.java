/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * TxDataState wraps a TxData and additional values in order to define the unique state to be signed.
 */
public class TxDataState {

    @SerializedName("chain_id")
    private String chainId;

    @SerializedName("data")
    private TxData txData;

    @SerializedName("nonce_time")
    private Date nonceTime;

    /**
     * TxDataState constructor.
     */
    public TxDataState(String chainId, Date nonceTime, TxData txData) {
        this.chainId = chainId;
        this.nonceTime = nonceTime;
        this.txData = txData;
    }
}
