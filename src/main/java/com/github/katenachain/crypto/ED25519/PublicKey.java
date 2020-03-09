/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.crypto.ED25519;

import com.github.katenachain.crypto.AbstractKey;
import com.github.katenachain.entity.account.KeyV1;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PublicKey is an Ed25519 public key wrapper (32 bytes).
 */
public class PublicKey extends AbstractKey {

    private EdDSANamedCurveSpec ed25519 = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);

    /**
     * PublicKey constructor with byte[].
     */
    public PublicKey(byte[] key) {
        super(key);
    }

    /**
     * indicates if a message and a signature match.
     */
    public boolean verify(byte[] message, byte[] signature) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        EdDSAEngine engine = new EdDSAEngine(MessageDigest.getInstance(ed25519.getHashAlgorithm()));
        EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(this.key, EdDSANamedCurveTable.getByName(ed25519.getHashAlgorithm()));
        EdDSAPublicKey pKey = new EdDSAPublicKey(spec);
        engine.initVerify(pKey);
        engine.update(message);
        return engine.verify(signature);
    }
}
