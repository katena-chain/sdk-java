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
    private String chainID;

    @SerializedName("data")
    private TxData data;

    @SerializedName("nonce_time")
    private Date nonceTime;

    /**
     * TxDataState constructor.
     */
    public TxDataState(String chainID, Date nonceTime, TxData data) {
        this.chainID = chainID;
        this.nonceTime = nonceTime;
        this.data = data;
    }
}
