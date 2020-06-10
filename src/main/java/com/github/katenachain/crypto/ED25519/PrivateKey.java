/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.crypto.ED25519;

import com.github.katenachain.crypto.AbstractKey;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;

import java.security.*;
import java.util.Arrays;

/**
 * PrivateKey is an Ed25519 private key wrapper (64 bytes).
 */
public class PrivateKey extends AbstractKey {

    private EdDSANamedCurveSpec ed25519 = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
    private PublicKey publicKey;

    /**
     * PrivateKey constructor with byte[].
     *
     * @param privateKey
     */
    public PrivateKey(byte[] privateKey) {
        super(privateKey);
        this.publicKey = new PublicKey(Arrays.copyOfRange(privateKey, 32, 64));
    }

    /**
     * @return
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    /**
     * accepts a message and returns its corresponding Ed25519 signature.
     *
     * @param message
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public byte[] sign(byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = new EdDSAEngine(MessageDigest.getInstance(ed25519.getHashAlgorithm()));

        EdDSAPrivateKeySpec keySpec = new EdDSAPrivateKeySpec(Arrays.copyOfRange(this.key, 0, 32), ed25519);
        java.security.PrivateKey sKey = new EdDSAPrivateKey(keySpec);
        signature.initSign(sKey);
        signature.update(message);
        return signature.sign();
    }
}
