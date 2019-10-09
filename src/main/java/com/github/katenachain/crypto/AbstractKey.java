/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.crypto;

/**
 * AbstractKey holds a key.
 */
public class AbstractKey {


    protected byte[] key;

    /**
     * AbstractKey constructor.
     */
    public AbstractKey(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return this.key;
    }
}
