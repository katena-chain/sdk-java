/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.utils;

import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.crypto.ED25519.PublicKey;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.Base64;

public class Crypto {

    /**
     * accepts a base64 encoded Ed25519 private key (88 chars) and returns an Ed25519 private key.
     *
     * @return
     */
    public static PrivateKey createPrivateKeyEd25519FromBase64(String privateKeyBase64) {
        return new PrivateKey(Base64.getDecoder().decode(privateKeyBase64));
    }

    /**
     * accepts a base64 encoded Ed25519 public key (44 chars) and returns an Ed25519 public key.
     *
     * @return
     */
    public static PublicKey createPublicKeyEd25519FromBase64(String publicKeyBase64) {
        return new PublicKey(Base64.getDecoder().decode(publicKeyBase64));
    }

    /**
     * generates a new ed25519 private key.
     *
     * @return
     * @throws InvalidAlgorithmParameterException
     */
    public static PrivateKey generateNewPrivateKeyEd25519() throws InvalidAlgorithmParameterException, IOException {
        KeyPairGenerator keyPairGenerator = new KeyPairGenerator();
        keyPairGenerator.initialize(EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519), new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        EdDSAPrivateKey privateKey = (EdDSAPrivateKey) keyPair.getPrivate();
        EdDSAPublicKey publicKey = (EdDSAPublicKey) keyPair.getPublic();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(privateKey.getSeed());
        outputStream.write(publicKey.getAbyte());

        return new PrivateKey(outputStream.toByteArray());
    }

    /**
     * accepts a base64 encoded X25519 private key (88 chars) and returns an X25519 private key.
     *
     * @return
     */
    public static com.github.katenachain.crypto.Nacl.PrivateKey createPrivateKeyX25519FromBase64(String privateKeyBase64) {
        return new com.github.katenachain.crypto.Nacl.PrivateKey(Base64.getDecoder().decode(privateKeyBase64));
    }

    /**
     * accepts a base64 encoded X25519 public key (44 chars) and returns an X25519 public key.
     *
     * @return
     */
    public static com.github.katenachain.crypto.Nacl.PublicKey createPublicKeyX25519FromBase64(String publicKeyBase64) {
        return new com.github.katenachain.crypto.Nacl.PublicKey(Base64.getDecoder().decode(publicKeyBase64));
    }
}
