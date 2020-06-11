/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity;

import java.util.HashMap;

/**
 * UnknownTxData is useful to deserialize and serialize back a tx data of unknown type.
 */
public class UnknownTxData implements TxData {

    private String type;
    private Object value;

    /**
     * UnknownTxData constructor.
     *
     * @param type
     * @param value
     */
    public UnknownTxData(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public String getNamespace() {
        return "";
    }

    public HashMap<String, String> getStateIds(String signerCompanyBcId) {
        return new HashMap<>();
    }

}
