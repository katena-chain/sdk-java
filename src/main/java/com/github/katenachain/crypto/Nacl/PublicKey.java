/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.crypto.Nacl;

import com.github.katenachain.crypto.AbstractKey;

import java.util.Base64;

/**
 * PublicKey is an X25519 public key wrapper (32 bytes).
 */
public class PublicKey extends AbstractKey {

    /**
     * PublicKey constructor with byte[].
     */
    public PublicKey(byte[] key) {
        super(key);
    }

    /**
     * PublicKey constructor with base64 String.
     */
    public PublicKey(String publicKeyBase64) {
        this(Base64.getDecoder().decode(publicKeyBase64));
    }
}
