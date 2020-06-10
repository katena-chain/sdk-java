/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.certify;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.crypto.Nacl.PublicKey;
import com.github.katenachain.utils.Common;

import java.util.HashMap;

/**
 * SecretNaclBoxV1 is the first version of a nacl box secret.
 */
public class SecretNaclBoxV1 implements TxData {

    private byte[] content;
    private String id;
    private byte[] nonce;
    private PublicKey sender;

    /**
     * SecretNaclBoxV1 constructor.
     *
     * @param id
     * @param content
     * @param nonce
     * @param sender
     */
    public SecretNaclBoxV1(byte[] content, String id, byte[] nonce, PublicKey sender) {
        this.content = content;
        this.id = id;
        this.nonce = nonce;
        this.sender = sender;
    }

    public String getId() {
        return this.id;
    }

    public PublicKey getSender() {
        return this.sender;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getContent() {
        return this.content;
    }

    @Override
    public String getType() {
        return Certify.getSecretNaclBoxV1Type();
    }

    @Override
    public String getNamespace() {
        return Certify.NAMESPACE;
    }

    @Override
    public HashMap<String, String> getStateIds(String signerCompanyBcId) {
        String id = this.id;
        return new HashMap<String, String>() {{
            put(Certify.getSecretIdKey(), Common.concatFqId(signerCompanyBcId, id));
        }};
    }
}
