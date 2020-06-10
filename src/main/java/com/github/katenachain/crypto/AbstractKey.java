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
     *
     * @param key
     */
    public AbstractKey(byte[] key) {
        this.key = key;
    }

    /**
     * @return
     */
    public byte[] getKey() {
        return this.key;
    }
}
