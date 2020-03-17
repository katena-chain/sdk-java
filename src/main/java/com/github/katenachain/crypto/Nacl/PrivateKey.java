/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.crypto.Nacl;

import com.github.katenachain.crypto.AbstractKey;
import org.abstractj.kalium.crypto.Box;

import java.util.*;

/**
 * PrivateKey is an X25519 private key wrapper (64 bytes).
 */
public class PrivateKey extends AbstractKey {

    private PublicKey publicKey;

    /**
     * PrivateKey constructor with byte[].
     * @param key
     */
    public PrivateKey(byte[] key) {
        super(key);
        this.publicKey = new PublicKey(Arrays.copyOfRange(key, 32, 64));
    }

    /**
     * PrivateKey constructor with base64 String.
     * @param privateKeyBase64
     */
    public PrivateKey(String privateKeyBase64) {
        this(Base64.getDecoder().decode(privateKeyBase64));
    }

    /**
     * encrypts a plain text message decipherable afterwards by the recipient private key.
     * @param message
     * @param recipientPublicKey
     * @return
     */
    public Hashtable<String, byte[]> seal(byte[] message, PublicKey recipientPublicKey) {
        Box box = new Box(recipientPublicKey.getKey(), Arrays.copyOfRange(this.key, 0, 32));
        Random rd = new Random();
        byte[] nonce = new byte[24];
        rd.nextBytes(nonce);
        byte[] encryptedMessage = box.encrypt(nonce, message);

        Hashtable<String, byte[]> result = new Hashtable<>();
        result.put("encryptedMessage", encryptedMessage);
        result.put("nonce", nonce);
        return result;
    }

    /**
     * decrypts an encrypted message with the appropriate sender information.
     * @param encryptedMessage
     * @param nonce
     * @param senderPublicKey
     * @return
     */
    public byte[] open(byte[] encryptedMessage, PublicKey senderPublicKey, byte[] nonce) {
        Box box = new Box(senderPublicKey.getKey(), Arrays.copyOfRange(this.key, 0, 32));
        try {
            return box.decrypt(nonce, encryptedMessage);
        } catch (Exception e) {
            return new byte[0];
        }


    }

    /**
     * @return
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
}
