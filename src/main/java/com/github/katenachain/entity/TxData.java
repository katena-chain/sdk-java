/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity;

import java.util.HashMap;

/**
 * TxData interface defines the methods a concrete TxData must implement.
 */
public interface TxData {
    String getType();

    String getNamespace();

    HashMap<String, String> getStateIds(String signerCompanyBcId);
}
