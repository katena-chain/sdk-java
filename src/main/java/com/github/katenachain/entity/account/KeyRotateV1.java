/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.account;

import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.utils.Common;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * KeyCreateV1 is the first version of a key rotate message.
 */
public class KeyRotateV1 implements TxData {

    private String id;

    @SerializedName("public_key")
    private PublicKey publicKey;

    /**
     * KeyCreateV1 constructor.
     *
     * @param id
     * @param publicKey
     */
    public KeyRotateV1(String id, PublicKey publicKey) {
        this.id = id;
        this.publicKey = publicKey;
    }

    public String getId() {
        return this.id;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getType() {
        return Account.getKeyRotateV1Type();
    }

    @Override
    public String getNamespace() {
        return Account.NAMESPACE;
    }

    @Override
    public HashMap<String, String> getStateIds(String signerCompanyBcId) {
        String id = this.id;
        return new HashMap<String, String>() {{
            put(Account.getKeyIdKey(), Common.concatFqId(signerCompanyBcId, id));
        }};
    }
}
