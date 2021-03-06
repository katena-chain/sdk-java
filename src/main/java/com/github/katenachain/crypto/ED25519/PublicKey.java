/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.crypto.ED25519;

import com.github.katenachain.crypto.AbstractKey;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * PublicKey is an Ed25519 public key wrapper (32 bytes).
 */
public class PublicKey extends AbstractKey {

    private EdDSANamedCurveSpec ed25519 = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);

    /**
     * PublicKey constructor with byte[].
     *
     * @param key
     */
    public PublicKey(byte[] key) {
        super(key);
    }

    /**
     * indicates if a message and a signature match.
     *
     * @param message
     * @param signature
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public boolean verify(byte[] message, byte[] signature) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        EdDSAEngine engine = new EdDSAEngine(MessageDigest.getInstance(ed25519.getHashAlgorithm()));
        EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(this.key, ed25519);
        EdDSAPublicKey pKey = new EdDSAPublicKey(spec);
        engine.initVerify(pKey);
        engine.update(message);
        return engine.verify(signature);
    }
}
